package com.dar.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :wx
 * @description : 一级分类
 * @create :2022-02-06 14:22:00
 */
@Data
public class OneSubject {
    private String id;
    private String title;

    //一级分类有多个二级分类
    private List<TwoSubject> children = new ArrayList<>();
}
