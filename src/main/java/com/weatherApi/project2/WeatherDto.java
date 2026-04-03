package com.weatherApi.project2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class WeatherDto {
    private String cityName;
    private double temperature;
    private String description;

}
