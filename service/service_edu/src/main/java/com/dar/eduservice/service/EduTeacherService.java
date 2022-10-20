package com.dar.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dar.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author wx
 * @since 2022-01-21
 */
public interface EduTeacherService extends IService<EduTeacher> {
    /**
     * 分页讲师列表
     * @param pageParam
     * @return
     */
    public Map<String, Object> pageListWeb(Page<EduTeacher> pageParam);
}
