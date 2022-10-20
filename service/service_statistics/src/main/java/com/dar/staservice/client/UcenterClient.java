package com.dar.staservice.client;

import com.dar.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author :wx
 * @description :
 * @create :2022-10-15 15:51:00
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    @GetMapping(value = "/educenter/member/countregister/{day}")
    public R registerCount(@PathVariable("day") String day);
}
