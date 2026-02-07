    package com.example.Weather.Forecasting.model;
    
    import jakarta.persistence.*;
    
    @Entity
    public class Weather {
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
    
        private String city;
        private double temperature;
        private double humidity;
        private String description;
    
        // For map feature
        private double latitude;
        private double longitude;
    
        public Weather() {}
    
        // Getters
        public Long getId() { return id; }
        public String getCity() { return city; }
        public double getTemperature() { return temperature; }
        public double getHumidity() { return humidity; }
        public String getDescription() { return description; }
        public double getLatitude() { return latitude; }
        public double getLongitude() { return longitude; }
    
        // Setters
        public void setId(Long id) { this.id = id; }
        public void setCity(String city) { this.city = city; }
        public void setTemperature(double temperature) { this.temperature = temperature; }
        public void setHumidity(double humidity) { this.humidity = humidity; }
        public void setDescription(String description) { this.description = description; }
        public void setLatitude(double latitude) { this.latitude = latitude; }
        public void setLongitude(double longitude) { this.longitude = longitude; }
    }
