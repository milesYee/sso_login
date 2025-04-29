package com.miles.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miles.entity.FileAttach;

import java.util.List;

public interface IFileAttachService extends IService<FileAttach> {

    public List<String> listFiles(String tagName);
}
