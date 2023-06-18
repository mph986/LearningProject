package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

/**
 * @author mph
 * @version 1.0
 * @date 2023/6/15 9:30
 * @description 课程分类信息接口
 */
public interface CourseCategoryService {


    public List<CourseCategoryTreeDto> queryTreeNodes(String id);
}
