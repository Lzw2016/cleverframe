package org.cleverframe.filemanager.controller;

import org.cleverframe.common.codec.EncodeDecodeUtils;
import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.filemanager.FilemanagerBeanNames;
import org.cleverframe.filemanager.entity.FileInfo;
import org.cleverframe.filemanager.service.IStorageService;
import org.cleverframe.filemanager.vo.request.DeleteFileVo;
import org.cleverframe.filemanager.vo.request.DownloadFileVo;
import org.cleverframe.filemanager.vo.request.FileUploadLazyVo;
import org.cleverframe.filemanager.vo.request.GetFileInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/17 21:04 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${base.mvcPath}/filemanager/manager")
public class FileManagerController extends BaseController {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(FileManagerController.class);

    @Autowired
    @Qualifier(FilemanagerBeanNames.LocalStorageService)
//    @Qualifier(FilemanagerBeanNames.FtpStorageService)
//    @Qualifier(FilemanagerBeanNames.FastDfsStorageService)
    private IStorageService storageService;

    @RequestMapping("/Demo" + VIEW_PAGE_SUFFIX)
    public ModelAndView getLoginJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("filemanager/Demo");
    }

    @RequestMapping("/WebUploader" + VIEW_PAGE_SUFFIX)
    public ModelAndView getWebUploaderJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("filemanager/WebUploader");
    }

    /**
     * 文件上传存储到当前服务器磁盘，支持多文件上传<br>
     * <p>
     * <b>注意：建议先使用 {@link #uploadLazy} 方法进行秒传，秒传失败再使用此方法</b>
     */
    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public AjaxMessage<List<FileInfo>> upload(HttpServletRequest request, HttpServletResponse response) {
        AjaxMessage<List<FileInfo>> message = new AjaxMessage<>(true, "文件上传成功", null);
        if (!(request instanceof MultipartHttpServletRequest)) {
            message.setSuccess(false);
            message.setFailMessage("当前请求并非上传文件的请求");
            return message;
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
            return message;
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
                FileInfo fileInfo = storageService.saveFile(uploadTimeAvg, "TEST", mFile);
                fileInfoList.add(fileInfo);
            } catch (Throwable e) {
                logger.error("文件上传失败", e);
            }
        }
        if (fileCount <= 0) {
            message.setSuccess(false);
            message.setFailMessage("此请求没有上传文件");
            return message;
        }
        message.setResult(fileInfoList);
        message.setSuccessMessage("一共上传文件数量" + fileCount + "个，上传成功数量" + fileInfoList.size() + "个");
        return message;
    }

    /**
     * 通过文件签名实现文件秒传，只能一次上传一个文件<br/>
     * 文件秒传：实际上是通过文件签名在以前上传的文件中找出一样的文件，并未真正的上传文件<br/>
     * <b>注意：文件秒传取决于此文件已经上传过了而且使用的文件签名类型相同</b>
     */
    @ResponseBody
    @RequestMapping("/uploadLazy")
    public AjaxMessage<FileInfo> uploadLazy(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid FileUploadLazyVo fileUploadLazyVo,
            BindingResult bindingResult) {
        AjaxMessage<FileInfo> message = new AjaxMessage<>(true, "文件秒传成功", null);
        if (!beanValidator(bindingResult, message)) {
            return message;
        }

        // 验证是否是Hex编码
        String fileDigest = fileUploadLazyVo.getFileDigest().toLowerCase();
        if (!EncodeDecodeUtils.isHexcode(fileDigest)) {
            message.setSuccess(false);
            message.setFailMessage("文件签名必须使用Hex编码");
            return message;
        }
        Character digestType;
        // 验证文件签名长度
        if (FileInfo.MD5_DIGEST.toString().equals(fileUploadLazyVo.getDigestType())) {
            digestType = FileInfo.MD5_DIGEST;
            // 验证长度MD5 长度为：32
            if (fileDigest.length() != 32) {
                message.setSuccess(false);
                message.setFailMessage("文件签名长度不正确(MD5签名长度32，SHA1签名长度40)");
                return message;
            }
        } else if (FileInfo.SHA1_DIGEST.toString().equals(fileUploadLazyVo.getDigestType())) {
            digestType = FileInfo.SHA1_DIGEST;
            // 验证长度SHA1 长度为：40
            if (fileDigest.length() != 40) {
                message.setSuccess(false);
                message.setFailMessage("文件签名长度不正确(MD5签名长度32，SHA1签名长度40)");
                return message;
            }
        } else {
            message.setSuccess(false);
            message.setFailMessage("不支持的文件签名：" + fileUploadLazyVo.getDigestType());
            return message;
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
            return message;
        }
        message.setResult(fileInfo);
        return message;
    }

    /**
     * 根据文件UUID，下载文件
     */
    @ResponseBody
    @RequestMapping("/download")
    public AjaxMessage download(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid DownloadFileVo downloadFileVo,
            BindingResult bindingResult) {
        AjaxMessage message = new AjaxMessage(false, null, "文件不存在");
        if (!beanValidator(bindingResult, message)) {
            return message;
        }
        try {
            // 使用文件 ID 不合适，建议使用 newName
            FileInfo fileInfo = storageService.isExists(downloadFileVo.getUuid());
            if (fileInfo == null) {
                return message;
            }
            // 文件存在，下载文件
            response.setContentType("multipart/form-data");
            String fileName = EncodeDecodeUtils.browserDownloadFileName(request.getHeader("User-Agent"), fileInfo.getFileName());
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            response.setHeader("Content-Length", fileInfo.getFileSize().toString());
            OutputStream outputStream = response.getOutputStream();
            fileInfo = storageService.openFileSpeedLimit(downloadFileVo.getUuid(), outputStream, -1);
            // outputStream.close(); //Servlet容器会关闭
            if (fileInfo != null) {
                logger.info("文件下载成功, 文件UUID={}", fileInfo.getUuid());
                return null;
            }
        } catch (Exception e) {
            logger.info("文件下载异常", e);
            message.setSuccess(false);
            message.setFailMessage("已取消下载文件");
            message.setException(e);
        }
        return message;
    }

    /**
     * 获取文件信息
     */
    @ResponseBody
    @RequestMapping("/getFileInfo")
    public AjaxMessage<FileInfo> getFileInfo(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid GetFileInfoVo getFileInfoVo,
            BindingResult bindingResult) {
        AjaxMessage<FileInfo> message = new AjaxMessage<>(true, "获取文件信息成功", null);
        if (beanValidator(bindingResult, message)) {
            // 使用文件 ID 不合适，建议使用 newName
            FileInfo fileInfo = null;
            try {
                fileInfo = storageService.isExists(getFileInfoVo.getUuid());
            } catch (Exception e) {
                logger.info("查询文件信息失败", e);
                message.setSuccess(false);
                message.setFailMessage("获取文件信息失败");
                message.setException(e);
            }
            message.setResult(fileInfo);
        }
        return message;
    }

    /**
     * 删除文件 或 删除文件信息
     */
    @ResponseBody
    @RequestMapping("/delete")
    public AjaxMessage deleteFile(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid DeleteFileVo deleteFileVo,
            BindingResult bindingResult) {
        AjaxMessage message = new AjaxMessage(true, "删除文件成功", null);
        if (beanValidator(bindingResult, message)) {
            try {
                int result = storageService.deleteFile(deleteFileVo.getUuid(), deleteFileVo.getLazy());
                switch (result) {
                    case 1:
                        message.setSuccessMessage("删除文件引用与文件");
                        break;
                    case 2:
                        message.setSuccessMessage("删除文件引用，未删除文件");
                        break;
                    case 3:
                        message.setSuccessMessage("文件不存在");
                        break;
                    default:
                        throw new RuntimeException("删除文件返回状态未知: " + result);
                }
            } catch (Exception e) {
                logger.info("删除文件异常", e);
                message.setSuccess(false);
                message.setFailMessage("删除文件失败");
                message.setException(e);
            }
        }
        return message;
    }
}
