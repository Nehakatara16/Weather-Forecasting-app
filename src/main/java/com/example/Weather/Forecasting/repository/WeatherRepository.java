package com.example.Weather.Forecasting.repository;

import com.example.Weather.Forecasting.model.Weather; // <-- THIS LINE WAS MISSING
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
}
