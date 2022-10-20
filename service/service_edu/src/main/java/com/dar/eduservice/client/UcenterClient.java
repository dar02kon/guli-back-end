package com.dar.eduservice.client;

/**
 * @author :wx
 * @description :
 * @create :2022-10-12 17:05:00
 */

import com.dar.eduservice.entity.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name="service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {
    //根据用户id获取用户信息
    @GetMapping("/educenter/member/getInfoUc/{id}")
    public UcenterMember getUcenterPay(@PathVariable("id") String id);
}
