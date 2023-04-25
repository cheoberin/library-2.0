package com.library.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}


	/* @Autowired
    private DbService dbService;
    @Bean
    public void initDb() {
        dbService.dbInit();

    }*/


}
