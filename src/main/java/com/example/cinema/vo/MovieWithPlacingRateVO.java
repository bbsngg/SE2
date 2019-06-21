package com.example.cinema.vo;

public class MovieWithPlacingRateVO {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPlacingRate() {
        return placingRate;
    }

    public void setPlacingRate(double placingRate) {
        this.placingRate = placingRate;
    }

    /**
     * 电影名
     */
    private String name;

    private double placingRate;
}
