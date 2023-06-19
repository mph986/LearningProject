package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mph
 * @version 1.0
 * @date 2023/6/18 15:10
 * @description 课程计划管理相关接口
 */
public interface TeachplanService {

    /**
     * 根据课程id查询课程计划
     * @param courseId 课程id
     * @return 课程计划
     */
    public List<TeachplanDto> findTeachplanTree(Long courseId);

    /**
     * 新增、修改、保存课程计划
     * @param saveTeachplanDto 课程计划
     */
    public void saveTeachplan(SaveTeachplanDto saveTeachplanDto);
}
