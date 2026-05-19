package com.weatherApi.project2;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    private final RestTemplate restTemplate;
    @Value("${weather.api.key}")
    private String apiKey;
    @Value("${weather.api.url}")
    private String apiUrl;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
@Cacheable(value = "weatherCache",key = "#city")
    public WeatherDto getWeatherDataOfCity(String city) {
        String weatherUrl = apiUrl + "?q=" + city + "&appid=" + apiKey + "&units=metric";
        WeatherResponceDto weatherResponce = restTemplate.getForObject(weatherUrl, WeatherResponceDto.class);
        assert weatherResponce != null;
        return new WeatherDto(weatherResponce.getCityName(),
                weatherResponce.getMain().getTemperature(),
                weatherResponce.getWeather().getFirst().getDescription());
    }

}
