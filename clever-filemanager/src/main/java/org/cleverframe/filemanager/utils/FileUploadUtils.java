package org.cleverframe.filemanager.utils;

import org.cleverframe.common.codec.EncodeDecodeUtils;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.filemanager.entity.FileInfo;
import org.cleverframe.filemanager.service.IStorageService;
import org.cleverframe.filemanager.vo.request.FileUploadLazyVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：LiZW <br/>
 * 创建时间：2017/1/17 17:01 <br/>
 */
public class FileUploadUtils {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(FileUploadUtils.class);

    /**
     * 上传文件通用方法
     *
     * @return 失败返回null, 务必调用 message.setResult(fileInfoList)
     */
    public static List<FileInfo> upload(String fileSource, IStorageService storageService, HttpServletRequest request, HttpServletResponse response, AjaxMessage message) {
        if (!(request instanceof MultipartHttpServletRequest)) {
            message.setSuccess(false);
            message.setFailMessage("当前请求并非上传文件的请求");
            return null;
        }
        // 保存上传文件
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        long uploadStart = System.currentTimeMillis();
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        long uploadEnd = System.currentTimeMillis();
        // 计算上传时间
        int fileCount = 0;
        for (String fileName : fileMap.keySet()) {
            MultipartFile mFile = fileMap.get(fileName);
            if (mFile.isEmpty()) {
                continue;
            }
            fileCount++;
        }
        if (fileCount <= 0) {
            message.setSuccess(false);
            message.setFailMessage("请选择上传的文件");
            return null;
        }
        long uploadTimeSum = uploadEnd - uploadStart;
        long uploadTimeAvg = uploadTimeSum / fileCount;
        logger.info("总共上传文件数量{}个,总共上传时间{}ms. 平均每个文件上传时间{}ms", fileCount, uploadTimeSum, uploadTimeAvg);
        List<FileInfo> fileInfoList = new ArrayList<>();
        for (String fileName : fileMap.keySet()) {
            MultipartFile mFile = fileMap.get(fileName);
            if (mFile.isEmpty()) {
                continue;
            }
            try {
                FileInfo fileInfo = storageService.saveFile(uploadTimeAvg, fileSource, mFile);
                fileInfoList.add(fileInfo);
            } catch (Throwable e) {
                logger.error("文件上传失败", e);
            }
        }
        if (fileCount <= 0) {
            message.setSuccess(false);
            message.setFailMessage("此请求没有上传文件");
            return null;
        }
        message.setSuccessMessage("一共上传文件数量" + fileCount + "个，上传成功数量" + fileInfoList.size() + "个");
        return fileInfoList;
    }

    /**
     * 通过文件签名实现文件秒传，只能一次上传一个文件<br/>
     *
     * @return 失败返回null, 务必调用 message.setResult(fileInfo)
     */
    public static FileInfo uploadLazy(IStorageService storageService, HttpServletRequest request, HttpServletResponse response, FileUploadLazyVo fileUploadLazyVo, AjaxMessage message) {
        // 验证是否是Hex编码
        String fileDigest = fileUploadLazyVo.getFileDigest().toLowerCase();
        if (!EncodeDecodeUtils.isHexcode(fileDigest)) {
            message.setSuccess(false);
            message.setFailMessage("文件签名必须使用Hex编码");
            return null;
        }
        Character digestType;
        // 验证文件签名长度
        if (FileInfo.MD5_DIGEST.toString().equals(fileUploadLazyVo.getDigestType())) {
            digestType = FileInfo.MD5_DIGEST;
            // 验证长度MD5 长度为：32
            if (fileDigest.length() != 32) {
                message.setSuccess(false);
                message.setFailMessage("文件签名长度不正确(MD5签名长度32，SHA1签名长度40)");
                return null;
            }
        } else if (FileInfo.SHA1_DIGEST.toString().equals(fileUploadLazyVo.getDigestType())) {
            digestType = FileInfo.SHA1_DIGEST;
            // 验证长度SHA1 长度为：40
            if (fileDigest.length() != 40) {
                message.setSuccess(false);
                message.setFailMessage("文件签名长度不正确(MD5签名长度32，SHA1签名长度40)");
                return null;
            }
        } else {
            message.setSuccess(false);
            message.setFailMessage("不支持的文件签名：" + fileUploadLazyVo.getDigestType());
            return null;
        }
        // 验证通过
        FileInfo fileInfo = null;
        try {
            fileInfo = storageService.lazySaveFile(fileUploadLazyVo.getFileName(), fileUploadLazyVo.getFileDigest(), digestType);
        } catch (Throwable e) {
            message.setSuccess(false);
            message.setFailMessage("上传文件失败，系统异常");
            message.setException(e);
        }
        if (fileInfo == null) {
            message.setSuccess(false);
            message.setFailMessage("文件秒传失败，该文件从未上传过");
            return null;
        }
        return fileInfo;
    }
}
