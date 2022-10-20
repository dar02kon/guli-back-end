package com.dar.ucenterservice.mapper;

import com.dar.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author wx
 * @since 2022-10-11
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {
    Integer selectRegisterCount(String day);
}
