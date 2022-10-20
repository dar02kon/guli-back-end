package com.dar.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author :wx
 * @description :
 * @create :2022-01-28 20:06:00
 */
@Data
public class DemoDate {
    //设置表头的名称
    @ExcelProperty(value = "学生编号",index = 0)
    private Integer sno;
    @ExcelProperty(value = "学生姓名",index = 1)
    private String sname;

}
