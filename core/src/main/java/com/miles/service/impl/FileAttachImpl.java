package com.miles.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miles.entity.FileAttach;
import com.miles.mapper.FileAttachMapper;
import com.miles.service.IFileAttachService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
@Slf4j
public class FileAttachImpl extends ServiceImpl<FileAttachMapper, FileAttach> implements IFileAttachService {

    @Override
    public List<String> listFiles(String tagName) {
        return getBaseMapper().listFiles(tagName);
    }
}
