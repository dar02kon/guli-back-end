package com.dar.eduservice.client;

import org.springframework.stereotype.Component;

/**
 * @author :wx
 * @description :
 * @create :2022-10-15 15:15:00
 */
@Component
public class OrderFile implements OrderClient{
    @Override
    public boolean isBuyCourse(String memberid, String id) {
        return true;
    }
}
