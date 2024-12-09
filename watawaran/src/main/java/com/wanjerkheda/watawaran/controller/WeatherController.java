package com.wanjerkheda.watawaran.controller;

import com.wanjerkheda.watawaran.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }


    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/weather")
    public String getWeatherDetails(@RequestParam("city") String city, Model model) {
        log.info("Requested city : " + city);
        model.addAttribute("weather", weatherService.getWeatherDetailsByCityName(city));
        return "home";
    }
}
