package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author huizhe
 * @date 2018/10/5
 * @time 14:09
 */
public interface IFileService {

    String upload(MultipartFile file, String path);
}
