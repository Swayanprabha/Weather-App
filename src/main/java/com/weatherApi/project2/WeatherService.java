package com.weatherApi.project2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
@Value("${weather.api.key}")
    private String apiKey;
@Value("${weather.api.url}")
    private String apiUrl;
private final RestTemplate restTemplate;
public WeatherService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
}
public WeatherDto getWeatherDataOfCity(String city)
{
    String weatherUrl=apiUrl+"?q="+city+"&appid="+apiKey+"&units=metric";
    WeatherResponceDto weatherResponce=restTemplate.getForObject(weatherUrl,WeatherResponceDto.class);
    WeatherDto weatherDto=new WeatherDto(weatherResponce.getCityName(),
            weatherResponce.getMain().getTemperature(),
            weatherResponce.getWeather().get(0).getDescription());
    return weatherDto;
}

}
