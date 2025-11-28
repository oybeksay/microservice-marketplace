package microservice.apigateway.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    private static List<String> WHITE_LIST = List.of(
            "/users/v1/auth/login",
            "/users/v1/auth/register"
    );

    public Predicate<ServerHttpRequest> isSecured = request ->
            WHITE_LIST.stream().noneMatch(uri -> request.getURI().getPath().equals(uri));

}
