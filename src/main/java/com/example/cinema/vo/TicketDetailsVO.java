package com.example.cinema.vo;

import com.example.cinema.po.Seat;

import java.util.Date;
import java.util.List;

public class TicketDetailsVO {
    private List<Seat> seats;
    private String movieName;
    private Date startTime;
    private Date endTime;

    public TicketDetailsVO(List<Seat> seats,String movieName,Date startTime,Date endTime){
        this.seats=seats;
        this.movieName=movieName;
        this.startTime=startTime;
        this.endTime=endTime;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
