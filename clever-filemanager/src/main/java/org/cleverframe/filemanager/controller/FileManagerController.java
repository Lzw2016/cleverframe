package org.cleverframe.filemanager.controller;

import org.cleverframe.common.codec.EncodeDecodeUtils;
import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.filemanager.FilemanagerBeanNames;
import org.cleverframe.filemanager.entity.FileInfo;
import org.cleverframe.filemanager.service.IStorageService;
import org.cleverframe.filemanager.utils.FileUploadUtils;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.OutputStream;
import java.util.List;

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
        List<FileInfo> fileInfoList = FileUploadUtils.upload("TEST", storageService, request, response, message);
        message.setResult(fileInfoList);
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
        FileInfo fileInfo = FileUploadUtils.uploadLazy(storageService, request, response, fileUploadLazyVo, message);
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
