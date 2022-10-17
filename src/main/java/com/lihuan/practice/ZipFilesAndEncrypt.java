package com.lihuan.practice;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

public class ZipFilesAndEncrypt {

    /**
     * @param srcFileName 待压缩文件路径
     * @param zipFileName zip文件名
     * @param password    加密密码
     * @return flag
     * @throws Exception
     * @Title: zipFilesAndEncrypt
     * @Description: 将指定路径下的文件压缩至指定zip文件，并以指定密码加密,若密码为空，则不进行加密保护
     */

    public void zipFilesAndEncrypt(String srcFileName, String zipFileName, String password) throws Exception {
        Logger logger = null;
        boolean flag;
        ZipOutputStream outputStream = null;
        System.out.println("进入测试类");
        if (StringUtils.isEmpty(srcFileName) || StringUtils.isEmpty(zipFileName)) {
            logger.error("请求的压缩路径或者文件名有误");
            return;
        }
        try {
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); //压缩方式
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); //压缩级别
            if (!StringUtils.isEmpty(password)) {
                parameters.setEncryptFiles(true);
                parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES); //加密方式
                parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
                parameters.setPassword(password); //设置密码
            }
            ArrayList filesToAdd = new ArrayList();
            File file = new File(srcFileName);
            File[] files = new File[0];
            if (file.isDirectory()) {
                files = file.listFiles();
                for (int i = 0; i < file.length(); i++) {
                    filesToAdd.add(new File(srcFileName + files[i].getName()));
                    System.out.println("文件名称：" + files[i].getName());
                }
            } else {
                filesToAdd.add(new File(srcFileName + file.getName()));
            }
            ZipFile zipFile = new ZipFile(srcFileName + zipFileName + ".zip");
            zipFile.addFiles(filesToAdd, parameters);
        } catch (Exception e) {
            System.out.println("文件压缩出错");
            logger.error("文件压缩出错", e);
            throw e;
        }
    }
}
