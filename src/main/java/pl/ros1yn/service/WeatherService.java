package pl.ros1yn.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.ros1yn.dto.WeatherDto;
import pl.ros1yn.mapper.WeatherMapper;
import pl.ros1yn.model.Weather;
import pl.ros1yn.utils.WeatherAPIPath;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class WeatherService {

    @Value("${openweather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final WeatherMapper weatherMapper;

    public WeatherService(WeatherMapper weatherMapper) {
        this.weatherMapper = weatherMapper;
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<Weather> getWeatherByCity(String city){

        try {

            String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
            String url = WeatherAPIPath.API_PATH.getUrl() + encodedCity
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
                    .body(new Weather("Authorisation error", "ERR", 0, 0));

        } catch (Exception e) {

            log.error(e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Weather("Server error", "ERR", 0, 0));
        }

    }

}
