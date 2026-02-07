package com.example.Weather.Forecasting.service;

import com.example.Weather.Forecasting.model.Weather;
import com.example.Weather.Forecasting.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    private final WeatherRepository repo;

    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherService(WeatherRepository repo) {
        this.repo = repo;
    }

    // 🌦 Current weather + save + map coords
    public Weather fetchWeather(String city) {

        try {
            String encodedCity = URLEncoder.encode(city.trim(), StandardCharsets.UTF_8);

            String url = "https://api.openweathermap.org/data/2.5/weather?q="
                    + encodedCity + ",IN&appid=" + apiKey + "&units=metric";

            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null || response.get("main") == null) {
                return null;
            }

            Map<String, Object> main = (Map<String, Object>) response.get("main");
            Map<String, Object> coord = (Map<String, Object>) response.get("coord");

            List<Map<String, Object>> weatherList =
                    (List<Map<String, Object>>) response.get("weather");

            Map<String, Object> weatherMap = weatherList.get(0);

            Weather record = new Weather();

            record.setCity(response.get("name").toString());
            record.setTemperature(Double.parseDouble(main.get("temp").toString()));
            record.setHumidity(Double.parseDouble(main.get("humidity").toString()));
            record.setDescription(weatherMap.get("description").toString());

            //map support
            record.setLatitude(Double.parseDouble(coord.get("lat").toString()));
            record.setLongitude(Double.parseDouble(coord.get("lon").toString()));

            return repo.save(record);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 5 day forecast
    public List<Map<String, Object>> fetchForecast(String city) {

        try {
            String encodedCity = URLEncoder.encode(city.trim(), StandardCharsets.UTF_8);

            String url = "https://api.openweathermap.org/data/2.5/forecast?q="
                    + encodedCity + ",IN&appid=" + apiKey + "&units=metric";

            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null) return null;

            return (List<Map<String, Object>>) response.get("list");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // weather history
    public List<Weather> getHistory() {
        return repo.findAll();
    }
}
