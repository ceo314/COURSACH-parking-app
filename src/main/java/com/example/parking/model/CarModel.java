package com.example.parking.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "car_model")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String modelName;

    private String bodyType;

    @Column(nullable = false)
    private Double rentalRate; // Тариф стоимости парковки за час
}
