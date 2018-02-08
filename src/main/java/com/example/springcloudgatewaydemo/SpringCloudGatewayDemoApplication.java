package com.example.springcloudgatewaydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringCloudGatewayDemoApplication {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/get")
                        .addRequestHeader("X-SpringOne", "Awesome!")
                        .uri("http://httpbin.org:80"))
                .route(r -> r.host("*.myhost.org")
                        .addRequestHeader("AnotherOne", "SomethingHere")
                        .uri("http://httpbin.org:80"))
                .route(r -> r.host("*.rewrite.org")
                        .rewritePath("/foo/(?<segment>.*)", "/${segment}")
                        .uri("http://httpbin.org:80"))
                .route(r -> r.host("*.setpath.org")
                        .and().path("/foo/{segment}")
                        .setPath("/{segment}")
                        .uri("http://httpbin.org:80"))
                .route(r -> r.host("www.hystrix.org")
                        .hystrix("mycmd")
                        .uri("http://httpbin.org:80"))
                .route(r -> r.path("/echo")
                        .uri("ws://localhost:9000"))
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudGatewayDemoApplication.class, args);
    }
}
