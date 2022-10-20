package com.dar.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :wx
 * @description :
 * @create :2022-01-21 20:26:00
 */
@Data
@AllArgsConstructor//有参构造
@NoArgsConstructor//无参构造
public class GuliException extends RuntimeException{

    private Integer code;//状态码

    private String msg;//异常信息

    @Override
    public String toString() {
        return "GuliException{" +
        "message=" + this.getMessage() +
        ", code=" + code +
        '}';
    }
}
