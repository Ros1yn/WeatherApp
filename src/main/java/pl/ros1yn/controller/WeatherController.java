package pl.ros1yn.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.ros1yn.model.Weather;
import pl.ros1yn.service.WeatherService;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("weather/{city}")
    ResponseEntity<Weather> getWeatherByCity(@PathVariable String city) {
        return weatherService.getWeatherByCity(city);
    }

}
