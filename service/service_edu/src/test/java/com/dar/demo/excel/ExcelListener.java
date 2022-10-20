package com.dar.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @author :wx
 * @description :
 * @create :2022-01-28 20:25:00
 */
public class ExcelListener extends AnalysisEventListener<DemoDate> {
    //一行一行读取数据
    @Override
    public void invoke(DemoDate demoDate, AnalysisContext analysisContext) {
        System.out.println("****" + demoDate);
    }
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头" + headMap);
    }
    //读完
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
