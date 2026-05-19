package com.weatherApi.project2;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Slf4j
@Service
public class WeitherPollingService {
    private final RestTemplate restTemplate;
    @Value("${weather.api.key}")
    private String apiKey;
    @Value("${weather.api.url}")
    private String apiUrl;

    WeitherPollingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private String createUrl(String city) {
        return apiUrl + "?q=" + city + "&appid=" + apiKey + "&units=metric";
    }
    @PostConstruct
    public void pollWeatherData() {
        String city = "Hyderabad";
        String weatherUrl = createUrl(city);
        Thread pollingThread = new Thread(() -> {
            while (true) {

                try {
                    WeatherResponceDto weatherResponce = restTemplate.getForObject(weatherUrl, WeatherResponceDto.class);
                    if (weatherResponce != null) {
                        var currentTiMe = java.time.LocalDateTime.now().toString();
                        log.info("Polled Weather Data at {} -> {}",currentTiMe, weatherResponce.getMain().getTemperature());

                    }
                    Thread.sleep(5000); // Poll every 50 seconds
                }
                catch(InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                    break;
                }
                catch(Exception e) {
                    log.info(e.getMessage());

                }
            }
        });
        pollingThread.setDaemon(true);
        pollingThread.setName("weather-polling-thread");
        pollingThread.start();
    }

}
