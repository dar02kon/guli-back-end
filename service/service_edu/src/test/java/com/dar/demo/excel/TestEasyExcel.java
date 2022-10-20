package com.dar.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :wx
 * @description :
 * @create :2022-01-28 20:08:00
 */
public class TestEasyExcel {
    public static void main(String[] args) {
        //实现excel写操作
        // 设置文件夹地址和文件名称
//        String fileName = "D:\\JavaSourceProgram\\JAVA_TheCrawler\\test_week\\winter_study\\mySource\\excel\\write.xlsx";
//
//        //调用方法实现操作
//        //第一个参数：文件路径名称  第二个参数：实体类class
//        EasyExcel.write(fileName,DemoDate.class).sheet("学生列表").doWrite(getData());

        //实现读取操作
        String fileName = "D:\\JavaSourceProgram\\JAVA_TheCrawler\\test_week\\winter_study\\mySource\\excel\\write.xlsx";
        EasyExcel.read(fileName,DemoDate.class,new ExcelListener()).sheet().doRead();
    }

    //返回list集合
    private static List<DemoDate> getData() {
        List<DemoDate> list = new ArrayList<>();
        for(int i=0; i<10; i++){
            DemoDate demoDate = new DemoDate();
            demoDate.setSno(i);
            demoDate.setSname("jack" + i);
            list.add(demoDate);
        }
        return list;
    }
}
