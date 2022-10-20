package com.dar.ucenterservice.controller;


import com.dar.commonutils.R;
import com.dar.commonutils.ordervo.UcenterMemberOrder;
import com.dar.commonutils.util.JwtUtils;
import com.dar.servicebase.exceptionhandler.GuliException;
import com.dar.ucenterservice.entity.UcenterMember;
import com.dar.ucenterservice.entity.vo.LoginInfoVo;
import com.dar.ucenterservice.entity.vo.LoginVo;
import com.dar.ucenterservice.entity.vo.RegisterVo;
import com.dar.ucenterservice.service.UcenterMemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author wx
 * @since 2022-10-11
 */
@RestController
@RequestMapping("/educenter/member")
//@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;

    @ApiOperation(value = "会员登录")
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo) {
        String token = memberService.login(loginVo);
        return R.ok().data("token", token);
    }

    @ApiOperation(value = "会员注册")
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo) {

        memberService.register(registerVo);
        return R.ok();
    }


    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        //调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据用户id获取用户信息
        UcenterMember member = memberService.getById(memberId);
        LoginInfoVo loginInfoVo = new LoginInfoVo();
        BeanUtils.copyProperties(member, loginInfoVo);
        return R.ok().data("userInfo", loginInfoVo);
    }


    //获取用户信息
    @GetMapping("getInfoUc/{id}")
    public UcenterMemberOrder getInfo(@PathVariable String id) {
        //根据用户id获取用户信息
        UcenterMember ucenterMember = memberService.getById(id);
        //把member对象里面值复制给UcenterMemberOrder对象
        UcenterMemberOrder member = new UcenterMemberOrder();
        BeanUtils.copyProperties(ucenterMember, member);
        return member;
    }

    @GetMapping(value = "countregister/{day}")
    public R registerCount(
            @PathVariable String day) {
        Integer count = memberService.countRegisterByDay(day);
        return R.ok().data("countRegister", count);
    }

}

