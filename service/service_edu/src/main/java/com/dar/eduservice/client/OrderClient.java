package com.dar.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author :wx
 * @description :
 * @create :2022-10-15 15:15:00
 */
@Component
@FeignClient(value = "service-order", fallback = OrderFile.class)
public interface OrderClient {
    //查询订单信息
    @GetMapping("/orderservice/order/isBuyCourse/{memberid}/{id}")
    public boolean isBuyCourse(@PathVariable("memberid") String memberid, @PathVariable("id") String id);
}
