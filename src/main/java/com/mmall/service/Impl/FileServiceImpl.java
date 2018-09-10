package com.mmall.service.Impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.net.ftp.FtpClient;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Lenovo
 * 日期: 2018-07-30
 * 时间: 19:04
 */
@Service
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    /**
     * 文件上传到服务器,使用到FTPUtil工具类
     * @param file
     * @param path
     * @return
     */
    @Override
    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;

        logger.error("开始上传文件,上传文件名:{},上传的路径{},上传的新文件名{}", fileName, path, uploadFileName);

        //将文件的路径放入fileDir中
        File fileDir = new File(path );
        if (!fileDir.exists()) {
            //文件若不存在,则进行创建
            fileDir.setWritable(true) ;
            fileDir.mkdirs() ;
        }

        File targetFile = new File(path, uploadFileName);

        try {
            //上传文件  将file转移到targetFile中
            file.transferTo(targetFile);
            //将targetFile上传到服务器上
            FTPUtil.uploadFile(Lists.newArrayList(targetFile)) ;
            //上传之后删除upload下的文件
            targetFile.delete() ;
        } catch (IOException e) {
            e.printStackTrace();
            return null ;
        }
        return targetFile.getName();
    }
}
