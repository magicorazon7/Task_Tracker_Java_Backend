package com.tracker.config;

import com.tracker.model.Role;
import com.tracker.model.User;
import com.tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitAdmin implements ApplicationRunner {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    @Override
    public void run(ApplicationArguments args) {
        if (repo.count() == 0) {
            System.out.println("Creating default ADMIN user...");

            User admin = User.builder()
                    .email("admin@example.com")
                    .password(encoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .build();

            repo.save(admin);

            System.out.println("✔ Default admin created: admin@example.com / admin123");
        }
    }
}
