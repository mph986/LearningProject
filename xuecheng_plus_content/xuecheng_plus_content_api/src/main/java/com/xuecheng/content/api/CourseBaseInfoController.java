package com.xuecheng.content.api;

import com.xuecheng.base.exception.ValidationGroups;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.service.CourseBaseInfoService;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author mph
 * @version 1.0
 * @date 2023/6/12 16:04
 * @description 课程管理接口
 */
@Api(value = "课程信息管理接口", tags = "课程信息管理接口")
@RestController
public class CourseBaseInfoController {

    @Autowired
    CourseBaseInfoService courseBaseInfoService;

    @ApiOperation("课程分页查询接口")
    @PostMapping("/course/list")
    public PageResult<CourseBase> list(
            PageParams pageParams,
            @RequestBody(required = false)
            QueryCourseParamsDto queryCourseParamsDto) {

        return courseBaseInfoService.
                queryCourseBaseList(pageParams, queryCourseParamsDto);
    }

    @ApiOperation("课程添加接口")
    @PostMapping("/course")
    public CourseBaseInfoDto createCourseBase(
            @RequestBody @Validated(ValidationGroups.Insert.class) AddCourseDto addCourseDto) {

        //获取到用户所属机构ID
        Long companyId = 1232141425L;
        return courseBaseInfoService.createCourseBase(companyId, addCourseDto);
    }

    @ApiOperation("根据id查询课程接口")
    @GetMapping("/course/{courseId}")
    public CourseBaseInfoDto getCourseBaseById(@PathVariable Long courseId) {

        return courseBaseInfoService.getCourseBaseInfo(courseId);
    }

    @ApiOperation("修改课程接口")
    @PutMapping("/course")
    public CourseBaseInfoDto modifyCourseBase(
            @RequestBody @Validated(ValidationGroups.Update.class)
            EditCourseDto editCourseDto) {
        //获取到用户所属机构ID
        Long companyId = 1232141425L;
        return courseBaseInfoService.updateCourseBase(companyId, editCourseDto);
    }
}
