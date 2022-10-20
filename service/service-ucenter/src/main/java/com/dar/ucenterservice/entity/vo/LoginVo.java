package com.dar.ucenterservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author :wx
 * @description :
 * @create :2022-10-11 08:39:00
 */

@Data
@ApiModel(value="登录对象", description="登录对象")
public class LoginVo {
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "密码")
    private String password;
}
