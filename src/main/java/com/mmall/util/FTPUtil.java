package com.mmall.util;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.ftp.FtpClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author Lenovo
 * 日期: 2018-07-30
 * 时间: 19:41
 */
public class FTPUtil {

    private static final Logger logger = LoggerFactory.getLogger(FtpClient.class);

    private static String ftpIp = PropertiesUtil.getProperty("ftp.server.ip");
    private static String ftpUser = PropertiesUtil.getProperty("ftp.user");
    private static String ftpPass = PropertiesUtil.getProperty("ftp.pass");

    private String ip;
    private int port;
    private String user;
    private String pwd;
    private FTPClient ftpClient;

    public FTPUtil(String ip, int port, String user, String pwd) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
    }

    /**
     * 判断文件是否上传成功
     *
     * @param fileList
     * @return
     */
    public static boolean uploadFile(List<File> fileList) throws IOException {
        FTPUtil ftpUtil = new FTPUtil(ftpIp, 21, ftpUser, ftpPass);
        logger.info("开始连接ftp服务器");
        boolean result = ftpUtil.uploadFile("img",fileList) ;
        logger.info("开始连接ftp服务器,结束上传,上传结果:{}");
        return result ;
    }

    /**
     * 文件上传逻辑类
     *
     * @param remotePath 多级目录
     * @param fileList
     * @return
     */
    private boolean uploadFile(String remotePath, List<File> fileList) throws IOException {
        boolean uploaded = true;
        FileInputStream fis = null;
        //连接FTP服务器
        if (connectServer(this.ip, this.port, this.user, this.pwd)) {
            try {
                //是否需要更换目录
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                //设置文件为二进制,防止乱码
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                //打开本地被动模式
                ftpClient.enterLocalPassiveMode();
                for (File fileItem : fileList) {
                    //将文件放到输入流中
                    fis = new FileInputStream(fileItem);
                    ftpClient.storeFile(fileItem.getName(), fis);
                }
            } catch (IOException e) {
                logger.error("上传文件异常", e);
                e.printStackTrace();
                uploaded = false;
            } finally {
                //关闭流,关闭服务器连接
                fis.close();
                ftpClient.disconnect();
            }
        }
        return uploaded;
    }

    /**
     * 连接服务器
     *
     * @return
     */
    private boolean connectServer(String ip, int port, String user, String pwd) {
        ftpClient = new FTPClient();
        //判断是否成功连接服务器 默认值为false
        boolean isSuccess = false;
        try {
            //连接服务器
            ftpClient.connect(ip);
            //登陆服务器
            ftpClient.login(user, pwd);
            isSuccess = true;
        } catch (IOException e) {
            logger.error("连接服务器异常", e);
        }
        return isSuccess;
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }


}
