package com.xuecheng.content.service;

import com.xuecheng.base.exception.ValidationGroups;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author mph
 * @version 1.0
 * @date 2023/6/13 15:40
 * @description 课程信息管理
 */
public interface CourseBaseInfoService {

    /**
     * 课程分页查询
     *
     * @param pageParams      分页查询参数
     * @param courseParamsDto 查询条件
     * @return 查询结果
     */
    public PageResult<CourseBase> queryCourseBaseList(
            PageParams pageParams, QueryCourseParamsDto courseParamsDto);

    /**
     * 新增课程
     *
     * @param companyId    机构id
     * @param addCourseDto 课程信息
     * @return 课程详细信息
     */
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);

    /**
     * 根据id查询课程
     *
     * @param courseId 课程id
     * @return 课程详细信息
     */
    public CourseBaseInfoDto getCourseBaseInfo(Long courseId);

    /**
     * @param companyId    机构id
     * @param editCourseDto 修改参数
     * @return 课程详细信息
     */
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto editCourseDto);
}
