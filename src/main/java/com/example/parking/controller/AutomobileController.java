package com.example.parking.controller;

import com.example.parking.model.Automobile;
import com.example.parking.model.User;
import com.example.parking.repository.AutomobileRepository;
import com.example.parking.repository.CarModelRepository;
import com.example.parking.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/automobiles")
public class AutomobileController {

    private final AutomobileRepository automobileRepository;
    private final CarModelRepository carModelRepository;
    private final UserRepository userRepository;

    public AutomobileController(AutomobileRepository automobileRepository,
                                CarModelRepository carModelRepository,
                                UserRepository userRepository) {
        this.automobileRepository = automobileRepository;
        this.carModelRepository = carModelRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String listAutomobiles(Model model) {
        model.addAttribute("automobiles", automobileRepository.findAll());
        model.addAttribute("models", carModelRepository.findAll());
        model.addAttribute("newAutomobile", new Automobile());
        return "automobiles";
    }

    @PostMapping("/add")
    public String addAutomobile(@ModelAttribute Automobile automobile, Authentication authentication) {
        automobile.setEntryTime(LocalDateTime.now());
        if (authentication != null) {
            User user = userRepository.findByUsername(authentication.getName()).orElse(null);
            automobile.setCreatedBy(user);
        }
        automobileRepository.save(automobile);
        return "redirect:/automobiles";
    }

    @GetMapping("/edit/{id}")
    public String editAutomobileForm(@PathVariable Long id, Model model) {
        Automobile automobile = automobileRepository.findById(id).orElseThrow();
        model.addAttribute("automobile", automobile);
        model.addAttribute("models", carModelRepository.findAll());
        return "edit-automobile";
    }

    @PostMapping("/edit/{id}")
    public String updateAutomobile(@PathVariable Long id, @ModelAttribute Automobile automobile) {
        Automobile existing = automobileRepository.findById(id).orElseThrow();
        existing.setLicensePlate(automobile.getLicensePlate());
        existing.setParkingSpot(automobile.getParkingSpot());
        existing.setCarModel(automobile.getCarModel());
        automobileRepository.save(existing);
        return "redirect:/automobiles";
    }

    @GetMapping("/delete/{id}")
    public String deleteAutomobile(@PathVariable Long id) {
        automobileRepository.deleteById(id);
        return "redirect:/automobiles";
    }
}
