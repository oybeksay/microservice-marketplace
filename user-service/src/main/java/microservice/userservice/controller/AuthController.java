package microservice.userservice.controller;

import microservice.userservice.dto.LoginRequestDto;
import microservice.userservice.dto.RegisterDto;
import microservice.userservice.dto.UserDto;
import microservice.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterDto dto) {
        return ResponseEntity.ok(userService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDto dto) {
        return ResponseEntity.ok(Map.of("token", userService.login(dto)));
    }
}
