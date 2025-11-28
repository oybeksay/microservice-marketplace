package microservice.apigateway.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import microservice.apigateway.config.RouterValidator;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthGlobalFilter implements GlobalFilter, Ordered {

    private final RouterValidator routerValidator;
    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
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

                String subject = jwtUtil.extractEmail(token);
                String role = jwtUtil.extractRole(token);
                Long userId = jwtUtil.extractUserId(token);
                System.out.println("Token tasdiqlandi. Foydalanuvchi: " + subject + ", Rol: " + role);
                ServerWebExchange modifiedExchange = exchange.mutate()
                        .request(builder -> {
                                    builder
                                            .header("X-Auth-UserId", String.valueOf(userId))
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
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        exchange.getResponse().setStatusCode(httpStatus);
        System.err.println("Filter xatosi: " + err);
        return exchange.getResponse().setComplete();
    }
}
