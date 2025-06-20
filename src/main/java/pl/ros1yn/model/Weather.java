package pl.ros1yn.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {

    private String city;
    private String country;
    private double temperature;
    private double windSpeed;

    public Weather(String city, String country, double temperature, double windSpeed) {
        this.city = city;
        this.country = country;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
    }

}
