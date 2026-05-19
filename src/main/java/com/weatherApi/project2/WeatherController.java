package com.weatherApi.project2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    @Autowired
    WeatherService weatherService;
    @GetMapping("/{city}")
    public WeatherDto getWeatherData(@PathVariable String city)
    {
        //call methods of service class to get weather
        WeatherDto weatherDto = weatherService.getWeatherDataOfCity(city);
        return weatherDto;
    }
}
