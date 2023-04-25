package com.library.apigateway.filter;


import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {


    public static final List<String> openApiEndpoints = List.of(
            "/eureka",
            "/api/book",
            "/api/author",
            "/api/genre",
            "/api/publisher",
            "/api/inventory"
    );

    public static final List<String> postEndpoints = List.of(
            "/auth/register",
            "/auth/login"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> {
                HttpMethod method = request.getMethod();
                String path = request.getURI().getPath();
                if (method == HttpMethod.GET && openApiEndpoints.stream().anyMatch(path::startsWith)) {
                    return false;
                }
                return method != HttpMethod.POST || postEndpoints.stream().noneMatch(path::startsWith); // liberar somente para POST se o endpoint não estiver na lista de endpoints seguros

            };
}
