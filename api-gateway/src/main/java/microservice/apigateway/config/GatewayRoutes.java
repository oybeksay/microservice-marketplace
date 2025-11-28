package microservice.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutes {

    @Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/users/**")
                        .filters(f -> f.rewritePath("/users/(?<remaining>.*)", "/${remaining}"))
                        .uri("lb://user-service"))
                .route("book-service", r -> r.path("/books/**")
                        .filters(f -> f.rewritePath("/books/(?<remaining>.*)", "/${remaining}"))
                        .uri("lb://book-service"))
                .build();
    }


}
