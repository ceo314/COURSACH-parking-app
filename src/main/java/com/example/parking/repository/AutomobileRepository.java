package com.example.parking.repository;

import com.example.parking.model.Automobile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutomobileRepository extends JpaRepository<Automobile, Long> {
}
