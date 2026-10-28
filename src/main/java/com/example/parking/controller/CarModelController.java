package com.example.parking.controller;

import com.example.parking.model.CarModel;
import com.example.parking.repository.CarModelRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/models")
public class CarModelController {

    private final CarModelRepository carModelRepository;

    public CarModelController(CarModelRepository carModelRepository) {
        this.carModelRepository = carModelRepository;
    }

    @GetMapping
    public String listModels(Model model) {
        model.addAttribute("carModels", carModelRepository.findAll());
        model.addAttribute("newModel", new CarModel());
        return "models";
    }

    @PostMapping("/add")
    public String addModel(@ModelAttribute CarModel carModel) {
        carModelRepository.save(carModel);
        return "redirect:/models";
    }

    @GetMapping("/edit/{id}")
    public String editModelForm(@PathVariable Long id, Model model) {
        CarModel carModel = carModelRepository.findById(id).orElseThrow();
        model.addAttribute("carModel", carModel);
        return "edit-model";
    }

    @PostMapping("/edit/{id}")
    public String updateModel(@PathVariable Long id, @ModelAttribute CarModel carModel) {
        CarModel existing = carModelRepository.findById(id).orElseThrow();
        existing.setBrand(carModel.getBrand());
        existing.setModelName(carModel.getModelName());
        existing.setBodyType(carModel.getBodyType());
        existing.setRentalRate(carModel.getRentalRate());
        carModelRepository.save(existing);
        return "redirect:/models";
    }

    @GetMapping("/delete/{id}")
    public String deleteModel(@PathVariable Long id) {
        carModelRepository.deleteById(id);
        return "redirect:/models";
    }
}
