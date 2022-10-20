package com.dar.orderservice.service.impl;

import com.dar.commonutils.ordervo.CourseWebVoOrder;
import com.dar.commonutils.ordervo.UcenterMemberOrder;
import com.dar.orderservice.client.EduClient;
import com.dar.orderservice.client.UcenterClient;
import com.dar.orderservice.entity.TOrder;
import com.dar.orderservice.mapper.TOrderMapper;
import com.dar.orderservice.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dar.orderservice.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author wx
 * @since 2022-10-15
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {
    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;

    //创建订单
    @Override
    public String saveOrder(String courseId, String memberId) {
        //远程调用课程服务，根据课程id获取课程信息
        CourseWebVoOrder courseDto = eduClient.getCourseInfoDto(courseId);
        //远程调用用户服务，根据用户id获取用户信息
        UcenterMemberOrder ucenterMember = ucenterClient.getInfo(memberId);
        //创建订单
        TOrder order = new TOrder();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseDto.getTitle());
        order.setCourseCover(courseDto.getCover());
        order.setTeacherName("test");
        order.setTotalFee(courseDto.getPrice());
        order.setMemberId(memberId);
        order.setMobile(ucenterMember.getMobile());
        order.setNickname(ucenterMember.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
