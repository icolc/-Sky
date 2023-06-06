package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.AliOSSUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author NXRUI
 * @version 1.0
 * @date 2023/6/6 19:47
 * @description:
 */
@Api(tags = "通用接口")
@Slf4j
@RestController
@RequestMapping("/admin/common")
public class CommonController {

    @Autowired
    private AliOSSUtils aliOSSUtils;

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        log.info("文件上传中:{}",file);
        String url = aliOSSUtils.upload(file);
        return Result.success(url);
    }
}
