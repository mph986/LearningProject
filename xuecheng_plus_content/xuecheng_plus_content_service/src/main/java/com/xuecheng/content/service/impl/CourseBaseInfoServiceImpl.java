package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.mapper.CourseMarketMapper;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.model.po.CourseMarket;
import com.xuecheng.content.service.CourseBaseInfoService;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author mph
 * @version 1.0
 * @date 2023/6/13 15:44
 * @description TODO
 */
@Slf4j
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {

    @Autowired
    CourseBaseMapper courseBaseMapper;
    @Autowired
    CourseMarketMapper courseMarketMapper;
    @Autowired
    CourseCategoryMapper courseCategoryMapper;

    @Override
    public PageResult<CourseBase> queryCourseBaseList(
            PageParams pageParams, QueryCourseParamsDto courseParamsDto) {

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
        //课程发布状态查询
        queryWrapper.eq(StringUtils.isNotEmpty(
                        courseParamsDto.getPublishStatus()),
                CourseBase::getStatus,
                courseParamsDto.getPublishStatus());

        //创建分页参数对象
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        //开始分页查询
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(page, queryWrapper);
        //数据列表
        List<CourseBase> items = pageResult.getRecords();
        //总记录数
        long total = pageResult.getTotal();

        return new PageResult<>(
                items, total, pageParams.getPageNo(), pageParams.getPageSize());

    }

    @Transactional
    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto dto) {

        //参数合法性校验
        if (StringUtils.isBlank(dto.getName()))
            throw new RuntimeException("课程名称为空");

        if (StringUtils.isBlank(dto.getMt()))
            throw new RuntimeException("课程分类为空");

        if (StringUtils.isBlank(dto.getSt()))
            throw new RuntimeException("课程分类为空");

        if (StringUtils.isBlank(dto.getGrade()))
            throw new RuntimeException("课程等级为空");

        if (StringUtils.isBlank(dto.getTeachmode()))
            throw new RuntimeException("教育模式为空");

        if (StringUtils.isBlank(dto.getUsers()))
            throw new RuntimeException("适应人群为空");

        if (StringUtils.isBlank(dto.getCharge()))
            throw new RuntimeException("收费规则为空");

        //向课程基本信息表写入数据
        CourseBase courseBaseNew = new CourseBase();
        //将传入页面的参数放入 courseBaseNew 对象
        //courseBaseNew.setName(dto.getName()); 简化写法。属性名称一致即可
        BeanUtils.copyProperties(dto, courseBaseNew);
        courseBaseNew.setCompanyId(companyId);
        courseBaseNew.setCreateDate(LocalDateTime.now());
        //审核状态默认未提交
        courseBaseNew.setAuditStatus("202002");
        //发布状态为未发布
        courseBaseNew.setStatus("203001");

        //插入数据库
        int insertRes = courseBaseMapper.insert(courseBaseNew);
        if (insertRes <= 0) throw new RuntimeException("添加失败");

        //向课程营销信息表course_market写入数据
        CourseMarket courseMarketNew = new CourseMarket();
        //将页面输入信息拷贝到courseMarketNew
        BeanUtils.copyProperties(dto, courseMarketNew);
        //主键：课程id
        Long courseId = courseBaseNew.getId();
        courseMarketNew.setId(courseId);
        //保存营销信息
        saveCourseMarket(courseMarketNew);
        //从数据库查询课程的详细信息
        return getCourseBaseInfo(courseId);
    }

    //查询课程信息
    public CourseBaseInfoDto getCourseBaseInfo(long courseId) {
        //从课程基本信息表查询
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null) return null;
        //从课程营销信息表查询
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        //查询分类信息，放入对象中
        //CourseCategory courseCategory = courseCategoryMapper.selectById(courseId);
        //组装
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase, courseBaseInfoDto);
        if (courseMarket != null) BeanUtils.copyProperties(courseMarket, courseBaseInfoDto);
        // BeanUtils.copyProperties(courseCategory, courseBaseInfoDto);
        return courseBaseInfoDto;
    }

    //定义一个方法保存营销信息，逻辑：存在则更新，不存在则添加
    private int saveCourseMarket(CourseMarket courseMarketNew) {
        //参数合法性校验
        String charge = courseMarketNew.getCharge();
        if (StringUtils.isEmpty(charge)) throw new RuntimeException("收费规则为空");
        //课程收费，价格为空
        if (charge.equals("201001"))
            if (courseMarketNew.getPrice() == null
                    || courseMarketNew.getPrice().floatValue() <= 0)
                throw new RuntimeException("课程价格必须大于0");
        //从数据库查询营销信息
        Long id = courseMarketNew.getId();
        CourseMarket courseMarket = courseMarketMapper.selectById(id);
        if (courseMarket == null) {
            //插入数据库
            return courseMarketMapper.insert(courseMarketNew);

        } else {
            //将courseMarketNew拷贝到courseMarket
            BeanUtils.copyProperties(courseMarketNew, courseMarket);
            courseMarket.setId(courseMarketNew.getId());
            //更新
            return courseMarketMapper.updateById(courseMarket);
        }
    }
}
