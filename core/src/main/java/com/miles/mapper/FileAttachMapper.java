package com.miles.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miles.entity.FileAttach;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FileAttachMapper extends BaseMapper<FileAttach> {

    public List<String> listFiles(@Param("tagName") String tagName);
}
