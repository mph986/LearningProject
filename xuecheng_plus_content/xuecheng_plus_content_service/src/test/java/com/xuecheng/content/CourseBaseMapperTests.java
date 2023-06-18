package com.xuecheng.content;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class CourseBaseMapperTests {

    @Autowired
    CourseBaseMapper courseBaseMapper;

    @Test
    void testCourseBaseMapper() {

        //组织参数
        //查询条件（请求体传入）
        QueryCourseParamsDto courseParamsDto = new QueryCourseParamsDto();
        courseParamsDto.setCourseName("Java");

        //条件构造器封装查询条件
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        //根据名称模糊查询,在sql中拼接 course_base.name like '%值%   有值，说明按课程名称查询
        queryWrapper.like(StringUtils.isNotEmpty(
                courseParamsDto.getCourseName()),
                CourseBase::getName,
                courseParamsDto.getCourseName());
        //根据课程审核状态查询 course_base.audit_status=？    有值，说明按课程审核状态查询
        queryWrapper.eq(StringUtils.isNotEmpty(
                courseParamsDto.getAuditStatus()),
                CourseBase::getAuditStatus,
                courseParamsDto.getAuditStatus());
        //根据课程发布状态查询 course_base.publish_status=？    有值，说明按课程发布状态查询
        queryWrapper.eq(StringUtils.isNotEmpty(
                        courseParamsDto.getPublishStatus()),
                CourseBase::getStatus,
                courseParamsDto.getPublishStatus());

        //分页参数（请求行传入）
        PageParams pageParams = new PageParams();
        pageParams.setPageNo(1L);
        pageParams.setPageSize(2L);

        //创建分页参数对象
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());


        //正式查询
        //开始分页查询
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(page, queryWrapper);
        //数据列表
        List<CourseBase> items = pageResult.getRecords();
        //总记录数
        long total = pageResult.getTotal();

        //查询
        PageResult<CourseBase> courseBasePageResult = new PageResult<>(
                items, total, pageParams.getPageNo(), pageParams.getPageSize());

        System.out.println(courseBasePageResult);
    }

}
