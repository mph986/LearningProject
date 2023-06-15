package com.xuecheng.content.api;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.service.CourseBaseInfoService;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation("课程查询接口")
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
            @RequestBody AddCourseDto addCourseDto) {

        //获取到用户所属机构ID
        Long companyId = 1232141425L;
        return courseBaseInfoService.createCourseBase(companyId, addCourseDto);
    }
}
