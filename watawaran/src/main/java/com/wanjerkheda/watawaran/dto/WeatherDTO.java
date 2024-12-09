package com.wanjerkheda.watawaran.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    public double latitude;
    public double longitude;
    public String resolvedAddress;
    public String address;
    public String description;
    public CurrentConditions currentConditions;
}
