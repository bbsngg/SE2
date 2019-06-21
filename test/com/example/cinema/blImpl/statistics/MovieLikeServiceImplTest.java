package com.example.cinema.blImpl.statistics;

import com.example.cinema.vo.DateLikeVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MovieLikeServiceImplTest {

    @Autowired
    MovieLikeServiceImpl service;

    @Test
    public void likeMovie() {
        service.likeMovie(12,16);//已经标记
        service.likeMovie(12,17);//无电影
        service.likeMovie(1,16);//Success
    }

    @Test
    public void unLikeMovie() {
        service.unLikeMovie(1,16);
    }

    @Test
    public void getCountOfLikes() {
        System.out.println(service.getCountOfLikes(16).getContent());
    }

    @Test
    public void getLikeNumsGroupByDate() {
//        List<DateLikeVO> list = (List<DateLikeVO>)service.getLikeNumsGroupByDate(16);

    }
}