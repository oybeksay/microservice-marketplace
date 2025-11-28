package microservice.apigateway.security;


import io.jsonwebtoken.Claims;
import microservice.apigateway.config.RouterValidator;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//agar har bir servicega alohida filter kerak bo'lsa, quyidagi filterdan foydalanish mumkin
/*
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final JwtUtil jwtUtil;
    private final RouterValidator routerValidator;

    public AuthenticationFilter(JwtUtil jwtUtil, RouterValidator routerValidator) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
        this.routerValidator = routerValidator;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            System.out.println("Request register");
            if (routerValidator.isSecured.test(exchange.getRequest())) {

                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return this.onError(exchange, "Authorization header is missing", HttpStatus.UNAUTHORIZED);
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                String token;

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    token = authHeader.substring(7);
                } else {
                    return this.onError(exchange, "Invalid authorization format. Must be 'Bearer <token>'.", HttpStatus.UNAUTHORIZED);
                }

                try {
                    if (!jwtUtil.validateToken(token)) {
                        return this.onError(exchange, "Invalid token", HttpStatus.UNAUTHORIZED);
                    }

                    Claims claims = jwtUtil.extractAllClaims(token);

                    String subject = claims.getSubject();
                    String role = claims.get("role", String.class);
                    System.out.println("Token tasdiqlandi. Foydalanuvchi: " + subject + ", Rol: " + role);
                    ServerWebExchange modifiedExchange = exchange.mutate()
                            .request(builder -> {
                                builder
                                                .header("X-Auth-User", subject)
                                                .header("X-Auth-Roles", role);
                                    }
                            ).build();

                    return chain.filter(modifiedExchange);

                } catch (Exception e) {
                    return this.onError(exchange, "Token validatsiyasida xato: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
                }
            }
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        exchange.getResponse().setStatusCode(httpStatus);
        System.err.println("Filter xatosi: " + err);
        return exchange.getResponse().setComplete();
    }

    public static class Config {
        // Configuration properties can be added here if needed
    }
}*/
