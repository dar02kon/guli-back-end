package com.dar.orderservice.service;

import com.dar.orderservice.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author wx
 * @since 2022-10-15
 */
public interface TOrderService extends IService<TOrder> {

    String saveOrder(String courseId, String memberIdByJwtToken);
}
