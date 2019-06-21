package com.example.cinema.blImpl.sales;

import com.example.cinema.po.Ticket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TicketServiceImplTest {

    @Autowired
    TicketServiceImpl service;

    @Test
    public void addTicket() {
    }

    @Test
    public void completeTicket() {
    }

    @Test
    public void getBySchedule() {
    }

    @Test
    public void getTicketByUser() {
    }

    @Test
    public void completeByVIPCard() {
    }

    @Test
    public void complete() {
    }

    @Test
    public void cancelTicket() {
    }

    @Test
    public void refundTicket() {
    }

    @Test
    public void getDetailsByTicketId() {
    }

    @Test
    public void selectRefStrategy() {
        System.out.println(service.selectRefStrategy("64"));
    }
}