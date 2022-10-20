package com.dar.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dar.eduservice.entity.EduSubject;
import com.dar.eduservice.entity.excel.SubjectData;
import com.dar.eduservice.service.EduSubjectService;
import com.dar.servicebase.exceptionhandler.GuliException;

/**
 * @author :wx
 * @description : 监听器不能交给spring管理，需要手动注入service
 * @create :2022-01-28 20:46:00
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    public EduSubjectService subjectService;

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }
    public SubjectExcelListener() {
    }

    //读取excel内容
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null){
            throw new GuliException(20001,"文件数据为空");
        }
        //一行一行读 一级分类  二级分类
        //先判断一级分类是否重复
        EduSubject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if(existOneSubject == null) {
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());//一级分类名称
            //没有相同的，添加数据库
            subjectService.save(existOneSubject);
        }

        String pid = existOneSubject.getId();
        //添加二级分类
        EduSubject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if(existTwoSubject == null) {
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());//二级分类名称
            //没有相同的，添加数据库
            subjectService.save(existTwoSubject);
        }
    }

    //判断一级分类是否重复添加
    private EduSubject existOneSubject(EduSubjectService subjectService,String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",0);
        return subjectService.getOne(wrapper);
    }

    //判断二级分类是否重复添加
    private EduSubject existTwoSubject(EduSubjectService subjectService,String name,String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        return subjectService.getOne(wrapper);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
