package com.dar.orderservice.client;

import com.dar.commonutils.ordervo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author :wx
 * @description :
 * @create :2022-10-15 14:03:00
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    //根据课程id查询课程信息
    @GetMapping("/educenter/member/getInfoUc/{id}")
    public UcenterMemberOrder getInfo(@PathVariable("id") String id);
}
