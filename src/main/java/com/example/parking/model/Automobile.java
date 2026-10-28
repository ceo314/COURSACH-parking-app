package com.example.parking.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "automobile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Automobile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String licensePlate;

    @Column(nullable = false)
    private String parkingSpot;

    @Column(nullable = false)
    private LocalDateTime entryTime;

    @ManyToOne
    @JoinColumn(name = "car_model_id", nullable = false)
    private CarModel carModel;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;
}
