package pl.ros1yn.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ros1yn.model.Weather;

@Component
@AllArgsConstructor
public class WeatherMapper {

    private final ObjectMapper objectMapper;

    public Weather extractWeatherData(String jsonResponse) {

        try {
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            JsonNode mainNode = jsonNode.path("main");
            JsonNode windNode = jsonNode.path("wind");
            JsonNode sysNode = jsonNode.path("sys");

            return build(jsonNode, mainNode, windNode, sysNode);


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public Weather build(JsonNode jsonNode, JsonNode mainNode, JsonNode windNode, JsonNode sysNode) {
        return Weather.builder()
                .city(jsonNode.path("name").asText())
                .temperature(mainNode.path("temp").asDouble())
                .windSpeed(windNode.path("speed").asDouble())
                .country(sysNode.path("country").asText())
                .build();
    }


}
