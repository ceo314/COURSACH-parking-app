package com.example.parking.config;

import com.example.parking.model.*;
import com.example.parking.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CarModelRepository carModelRepository;
    private final AutomobileRepository automobileRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository,
                           CarModelRepository carModelRepository, AutomobileRepository automobileRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.carModelRepository = carModelRepository;
        this.automobileRepository = automobileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Создаем роли
        Role roleReadOnly = roleRepository.save(new Role(null, "ROLE_READ_ONLY"));
        Role roleUser = roleRepository.save(new Role(null, "ROLE_USER"));
        Role roleAdmin = roleRepository.save(new Role(null, "ROLE_ADMIN"));

        // Создаем пользователей (пароли: pass123)
        User readonlyUser = new User(null, "guest", passwordEncoder.encode("pass123"), Set.of(roleReadOnly));
        User user = new User(null, "operator", passwordEncoder.encode("pass123"), Set.of(roleUser));
        User admin = new User(null, "admin", passwordEncoder.encode("pass123"), Set.of(roleAdmin));

        userRepository.save(readonlyUser);
        userRepository.save(user);
        userRepository.save(admin);

        // Создаем модели
        CarModel sedan = carModelRepository.save(new CarModel(null, "Toyota", "Camry", "Седан", 150.0));
        CarModel suv = carModelRepository.save(new CarModel(null, "BMW", "X5", "Кроссовер", 250.0));

        // Создаем автомобили на парковке
        automobileRepository.save(new Automobile(null, "А123АА777", "A-01", LocalDateTime.now().minusHours(2), sedan, user));
        automobileRepository.save(new Automobile(null, "В777ВВ999", "B-12", LocalDateTime.now().minusHours(5), suv, admin));
    }
}
