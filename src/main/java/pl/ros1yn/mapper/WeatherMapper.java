package pl.ros1yn.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import pl.ros1yn.dto.WeatherDto;
import pl.ros1yn.model.Weather;

@Component
@AllArgsConstructor
public class WeatherMapper {

    private final ObjectMapper objectMapper;

    public WeatherDto extractWeatherData(String jsonResponse) {

        try {
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            JsonNode mainNode = jsonNode.path("main");
            JsonNode windNode = jsonNode.path("wind");
            JsonNode sysNode = jsonNode.path("sys");

            return WeatherDto.builder()
                    .city(jsonNode.path("name").asText())
                    .temperature(mainNode.path("temp").asDouble())
                    .windSpeed(windNode.path("speed").asDouble())
                    .country(sysNode.path("country").asText())
                    .build();


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public Weather convertToWeather(WeatherDto weatherDto) {
        return Weather.builder()
                .city(weatherDto.getCity())
                .country(weatherDto.getCountry())
                .temperature(weatherDto.getTemperature())
                .windSpeed(weatherDto.getWindSpeed())
                .build();
    }
}
