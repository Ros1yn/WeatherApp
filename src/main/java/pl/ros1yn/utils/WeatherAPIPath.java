package pl.ros1yn.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WeatherAPIPath {

    API_PATH("https://api.openweathermap.org/data/2.5/weather?q=");

    private final String url;
}
