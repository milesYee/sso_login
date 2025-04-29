package com.miles.controller;



import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miles.config.MinioConfig;
import com.miles.entity.FileAttach;
import com.miles.service.IFileAttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/file")
public class FileController {

    @Autowired
    private MinioConfig minioConfig;
    @Autowired
    private IFileAttachService fileAttachService;

    // 上传
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        String url = minioConfig.putObject(multipartFile);
        FileAttach fileAttach = new FileAttach();
        String originalFilename = multipartFile.getOriginalFilename();
        fileAttach.setFileName(originalFilename);
        fileAttach.setFileFix(originalFilename.substring(originalFilename.lastIndexOf(".")+1));
        fileAttach.setFileTag("tag_test,tag_this,tag_a,tag_b,tag_c,tag_d");
        fileAttach.setFileUrl(url);
        fileAttach.setFileType("minio");
        fileAttachService.saveOrUpdate(fileAttach);
        return url;
    }

    // 下载文件
    @GetMapping("/download")
    public void download(@RequestParam("fileName")String fileName, HttpServletResponse response) {
        this.minioConfig.download(fileName,response);
    }

    // 列出所有存储桶名称
    @PostMapping("/list")
    public List<String> list() throws Exception {
        return this.minioConfig.listBucketNames();
    }

    // 创建存储桶
    @PostMapping("/createBucket")
    public boolean createBucket(String bucketName) throws Exception {
        return this.minioConfig.makeBucket(bucketName);
    }

    // 删除存储桶
    @PostMapping("/deleteBucket")
    public boolean deleteBucket(String bucketName) throws Exception {
        return this.minioConfig.removeBucket(bucketName);
    }

    // 列出存储桶中的所有对象名称
    @PostMapping("/listObjectNames")
    public List<String> listObjectNames(String bucketName) throws Exception {
        return this.minioConfig.listObjectNames(bucketName);
    }

    // 删除一个对象
    @PostMapping("/removeObject")
    public boolean removeObject(String bucketName, String objectName) throws Exception {
        return this.minioConfig.removeObject(bucketName, objectName);
    }

    // 文件访问路径
    @PostMapping("/getObjectUrl")
    public String getObjectUrl(String bucketName, String objectName) throws Exception {
        return this.minioConfig.getObjectUrl(bucketName, objectName);
    }

    @PostMapping("/listFiles")
    public List<String> listFiles(@RequestParam("tagName") String tagName) throws Exception {
        List<String> list = fileAttachService.listFiles(tagName);
        return list;
    }
}
