package com.dar.ucenterservice.service;

import com.dar.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dar.ucenterservice.entity.vo.LoginVo;
import com.dar.ucenterservice.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author wx
 * @since 2022-10-11
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid);

    Integer countRegisterByDay(String day);
}
