package com.wanjerkheda.watawaran.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CurrentConditions implements Serializable {

    private static final long serialVersionUID = 1L;

    public double temp;
}
