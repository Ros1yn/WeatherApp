package pl.ros1yn.view;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.ros1yn.model.Weather;
import pl.ros1yn.service.WeatherService;

@Controller
@RequiredArgsConstructor
class ViewController {

    private final WeatherService weatherService;

    private static final String WEATHER = "weather";

    @GetMapping("/weather")
    String home(Model model) {
        model.addAttribute("title", "Weather☀️");
        return WEATHER;
    }

    @PostMapping("/weather")
    String getWeather(@RequestParam String city, Model model) {
        ResponseEntity<Weather> response = weatherService.getWeatherByCity(city);
        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute(WEATHER, response.getBody());

            return "weather-result";
        } else {
            model.addAttribute("error", "City was not found, try again.");
            return WEATHER;
        }
    }
}
