package pl.ros1yn.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.ros1yn.dto.WeatherDto;
import pl.ros1yn.mapper.WeatherMapper;
import pl.ros1yn.model.Weather;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class WeatherService {

    @Value("${openweather.api.key}")
    private String apiKey;

    private final String API_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    private final RestTemplate restTemplate;
    private final WeatherMapper weatherMapper;

    public WeatherService(WeatherMapper weatherMapper) {
        this.weatherMapper = weatherMapper;
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<Weather> getWeatherByCity(String city){

        try {

            String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
            String url = API_URL + encodedCity
                    + "&appid=" + apiKey
                    + "&units=metric";

            String jsonResponse = restTemplate.getForObject(url, String.class);

            WeatherDto weatherDto = weatherMapper.extractWeatherData(jsonResponse);

            Weather weather = weatherMapper.convertToWeather(weatherDto);

            return ResponseEntity.ok(weather);

        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Weather(city, "N/A", 0, 0));

        } catch (HttpClientErrorException.Unauthorized e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new Weather("Błąd autoryzacji", "ERR", 0, 0));

        } catch (Exception e) {

            System.out.println(e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Weather("Błąd serwera", "ERR", 0, 0));
        }

    }


}
