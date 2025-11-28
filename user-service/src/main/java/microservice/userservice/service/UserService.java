package microservice.userservice.service;

import microservice.userservice.dto.LoginRequestDto;
import microservice.userservice.dto.RegisterDto;
import microservice.userservice.dto.UserDto;

public interface UserService {
    UserDto register(RegisterDto dto);
    String login(LoginRequestDto dto);
}
