package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Lenovo
 * 日期: 2018-07-30
 * 时间: 19:04
 */
public interface IFileService {

    String upload(MultipartFile file, String path) ;
}
