package com.example.cinema.data.sales;

import com.example.cinema.po.Ticket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TicketMapperTest {

    @Autowired
    TicketMapper mapper;

    @Test
    public void insertTicket() {
//        16	72	8	0	1	64	2019-05-20 21:36:16

        Ticket t = new Ticket();
        t.setUserId(15);
        t.setState(1);
        t.setScheduleId(72);
        t.setColumnIndex(8);
        t.setRowIndex(1);

        System.out.println(mapper.insertTicket(t));
    }

    @Test
    public void insertTickets() {
    }

    @Test
    public void deleteTicket() {
        System.out.println(mapper.deleteTicket(80)); //不存在 0
//        System.out.println(mapper.deleteTicket(1)); //存在 1

    }

    @Test
    public void updateTicketState() {
    }

    @Test
    public void selectTicketsBySchedule() {
    }

    @Test
    public void selectTicketByScheduleIdAndSeat() {
    }

    @Test
    public void selectTicketById() {
    }

    @Test
    public void selectTicketByUser() {
    }

    @Test
    public void cleanExpiredTicket() {
    }
}