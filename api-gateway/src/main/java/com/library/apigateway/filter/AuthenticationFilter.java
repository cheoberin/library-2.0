package com.library.apigateway.filter;

import com.library.apigateway.util.JwtUtil;
import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String ROLE_EMPLOYEE = "EMPLOYEE";
    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_CUSTOMER = "CUSTOMER";
    private static final List<String> ROLES_CUSTUMER_EMPLOYEE_ADMIN = List.of(ROLE_CUSTOMER ,ROLE_ADMIN,ROLE_EMPLOYEE);
    private static final List<String> ROLES_CUSTUMER_EMPLOYEE = List.of(ROLE_CUSTOMER ,ROLE_EMPLOYEE);
    private static final List<String> ROLES_CUSTOMER_ONLY = List.of(ROLE_CUSTOMER );
    private static final List<String> ROLES_EMPLOYEE_ADMIN = List.of(ROLE_ADMIN,ROLE_EMPLOYEE);

    @Autowired
    private RouteValidator routeValidator;
    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if(routeValidator.isSecured.test(exchange.getRequest())){
                String authorizationHeader = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION_HEADER);

                if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.FORBIDDEN);
                    String errorMessage = "Erro de autenticação: " + "Nenhum token presente";
                    byte[] errorBytes = errorMessage.getBytes(StandardCharsets.UTF_8);
                    DataBuffer buffer = response.bufferFactory().wrap(errorBytes);
                    response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    return response.writeWith(Mono.just(buffer));
                }

                String token = authorizationHeader.substring(BEARER_PREFIX.length());

                try{
                    jwtUtil.validateToken(token);
                }catch (Exception e){
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.FORBIDDEN);
                    String errorMessage = "Erro de autenticação: " + e.getMessage();
                    byte[] errorBytes = errorMessage.getBytes(StandardCharsets.UTF_8);
                    DataBuffer buffer = response.bufferFactory().wrap(errorBytes);
                    response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    return response.writeWith(Mono.just(buffer));
                }

                var roles  = jwtUtil.getRoles(token);

                boolean hasRole = CollectionUtils.containsAny(roles, ROLES_CUSTUMER_EMPLOYEE_ADMIN);


                if(CollectionUtils.containsAny(roles, ROLES_EMPLOYEE_ADMIN)) {
                    if (exchange.getRequest().getMethod() == HttpMethod.DELETE ||
                            exchange.getRequest().getMethod() == HttpMethod.POST ||
                            exchange.getRequest().getMethod() == HttpMethod.PUT||
                            exchange.getRequest().getMethod() == HttpMethod.OPTIONS||
                            exchange.getRequest().getMethod() == HttpMethod.GET) {
                        return chain.filter(exchange);
                    } else {
                        hasRole = false;
                    }
                }

                if (exchange.getRequest().getPath().toString().equals("/api/orderbook")) {
                    hasRole = CollectionUtils.containsAny(roles, ROLES_CUSTOMER_ONLY);
                }

                if(!hasRole){
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.FORBIDDEN);
                    String errorMessage = "Erro de autenticação: " + "Role não autorizada";
                    byte[] errorBytes = errorMessage.getBytes(StandardCharsets.UTF_8);
                    DataBuffer buffer = response.bufferFactory().wrap(errorBytes);
                    response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    return response.writeWith(Mono.just(buffer));
                }
            }
            return chain.filter(exchange);
        });
    }


    public static class Config{}

}
