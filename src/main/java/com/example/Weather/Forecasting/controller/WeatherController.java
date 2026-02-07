package com.example.Weather.Forecasting.controller;

import com.example.Weather.Forecasting.model.Weather;
import com.example.Weather.Forecasting.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public String getWeather(@RequestParam String city, Model model) {
        Weather weather = weatherService.fetchWeather(city);
        if (weather == null) {
            model.addAttribute("error", "City not found or API issue");
            return "index";
        }
        model.addAttribute("weather", weather);
        model.addAttribute("forecast", weatherService.fetchForecast(city));
        return "result";
    }

    @GetMapping("/history")
    public String history(Model model) {
        model.addAttribute("history", weatherService.getHistory());
        return "history";
    }

    // Updated /coords endpoint to return coords + weather data for map
    @GetMapping("/coords")
    public ResponseEntity<?> getCoords(@RequestParam String city) {
        try {
            Weather weather = weatherService.fetchWeather(city);
            if (weather != null) {
                Map<String, Object> data = new HashMap<>();
                data.put("lat", weather.getLatitude());
                data.put("lon", weather.getLongitude());
                data.put("temperature", weather.getTemperature());
                data.put("humidity", weather.getHumidity());
                data.put("description", weather.getDescription());
                return ResponseEntity.ok(data);  // Success: return coords + weather
            } else {
                return ResponseEntity.badRequest().body("City not found or API error");  // Error: return message
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Server error");  // Handle unexpected errors
        }
    }
}