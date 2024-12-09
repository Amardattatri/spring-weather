package com.wanjerkheda.watawaran.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanjerkheda.watawaran.dto.WeatherDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Slf4j
@Service
public class WeatherService {

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    @Value("${weather.api.key}")
    private String weatherApiKey;

    @Autowired
    private RestTemplate restTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    public WeatherService(RestTemplate restTemplate, StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }


    public WeatherDTO getWeatherDetailsByCityName(String city) {
        String cacheKey = "weather:" + city.trim().toUpperCase();
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        ObjectMapper objectMapper = new ObjectMapper();

        String cachedData = valueOperations.get(cacheKey);
        if (cachedData != null) {
            try {
                return objectMapper.readValue(cachedData, WeatherDTO.class);
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse cache data.");
            }
        }

        String constructUrl = weatherApiUrl + city + "?key=" + weatherApiKey;
        log.info("Going to call external url: " + constructUrl);
        WeatherDTO weatherInfo = restTemplate.getForObject(constructUrl, WeatherDTO.class);
        try {
            String cacheWeatherInfo = objectMapper.writeValueAsString(weatherInfo);
            valueOperations.set(cacheKey, cacheWeatherInfo, Duration.ofHours(12));
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse weather info to cache data.");
        }

        return weatherInfo;
    }
}
