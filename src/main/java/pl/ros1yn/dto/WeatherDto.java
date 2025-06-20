package pl.ros1yn.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDto {

    @JsonProperty("name")
    private String city;

    @JsonProperty("sys.country")
    private String country;

    @JsonProperty("main.temp")
    private double temperature;

    @JsonProperty("wind.speed")
    private double windSpeed;

}
