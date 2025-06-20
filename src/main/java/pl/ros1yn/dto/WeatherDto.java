package pl.ros1yn.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class OpenWeatherResponse {

    public class FlatWeatherDto {

        private String cityName;
        private String country;
        private double temperature;
        private double windSpeed;


    }
