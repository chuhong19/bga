package com.example.bga.auth.user;

import com.example.bga.auth.jwt.JwtUtil;
import com.example.bga.exception.InvalidException;
import com.example.bga.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$");

    public void register(String email, String password) {
        if (email == null || email.isEmpty() || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidException("Invalid email format");
        }
        if (password == null || password.isEmpty() || !PASSWORD_PATTERN.matcher(password).matches()) {
            throw new InvalidException("Password must be at least 8 characters long and include uppercase, lowercase, number, and special character");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new InvalidException("Email is already in use");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public String login(String email, String password) {
        if (email == null || email.isEmpty() || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidException("Invalid email format");
        }
        if (password == null || password.isEmpty()) {
            throw new InvalidException("Password cannot be empty");
        }
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        User user = userOpt.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidException("Incorrect password");
        }
        return jwtUtil.generateToken(email);
    }
}
