package com.dar.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dar.commonutils.R;
import com.dar.eduservice.entity.EduTeacher;
import com.dar.eduservice.entity.vo.TeacherQuery;
import com.dar.eduservice.service.EduTeacherService;
import com.dar.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author wx
 * @since 2022-01-21
 */
@Api(description="讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
//@CrossOrigin//跨域
public class EduTeacherController {

    //注入Service
    @Autowired
    private EduTeacherService teacherService;

    //查询讲师所有数据
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
//        try {
//            int i= 1/0;
//
//        } catch (Exception e){
//            //执行自定义异常
//            throw new GuliException(20001,"执行了自定义异常处理");
//        }

        //调用service方法
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    //逻辑删除讲师
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        boolean result = teacherService.removeById(id);
        if(result){
            return R.ok();
        }
        return R.error();
    }

    /**
     * 分页查询讲师信息
     * @param current   当前页
     * @param limit     每页记录数
     * @return 成功则返回相应的数据
     */
    @ApiOperation(value = "分页查询讲师信息")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(
            @ApiParam(name = "current", value = "当前页", required = true)
            @PathVariable long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
             @PathVariable long limit){

        //创建page对象
        Page<EduTeacher> page = new Page<>(current,limit);
        //调用方法实现分页
        teacherService.page(page,null);
        long total = page.getTotal();//总记录数
        List<EduTeacher> list = page.getRecords();//数据list集合
        return R.ok().data("total",total).data("rows",list);
    }

    //多条件查询带分页
    //get请求拿不到@RequestBody对应属性的值
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        //构造条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件值是否为空
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        } else if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level",level);
        }else if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create",begin);
        }else if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_modified",end);
        }
        wrapper.orderByDesc("gmt_create");
        teacherService.page(pageTeacher,wrapper);
        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> list = pageTeacher.getRecords();//数据list集合
        return R.ok().data("total",total).data("rows",list);
    }

    //添加讲师
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean result = teacherService.save(eduTeacher);
        if(result){
            return R.ok();
        }
        return R.error();
    }

    //根据讲师id查询
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    //讲师修改
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean result = teacherService.updateById(eduTeacher);
        if(result){
            return R.ok();
        }
        return R.error();
    }

}

