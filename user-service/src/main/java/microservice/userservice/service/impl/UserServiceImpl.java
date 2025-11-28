package microservice.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import microservice.userservice.dto.LoginRequestDto;
import microservice.userservice.dto.RegisterDto;
import microservice.userservice.dto.UserDto;
import microservice.userservice.entity.User;
import microservice.userservice.repository.UserRepository;
import microservice.userservice.security.JwtUtil;
import microservice.userservice.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public UserDto register(RegisterDto dto) {
        userRepository.findByEmail(dto.getEmail()).ifPresent(user -> {
            throw new RuntimeException("User with email " + dto.getEmail() + " already exists");
        });

        User user = User.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .role("USER")
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        User save = userRepository.save(user);
        return toDto(save);
    }

    @Override
    public String login(LoginRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User with email " + dto.getEmail() + " does not exist"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(user);
    }

    private UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
