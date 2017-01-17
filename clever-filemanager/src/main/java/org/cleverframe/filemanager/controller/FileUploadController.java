package org.cleverframe.filemanager.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.filemanager.FilemanagerBeanNames;
import org.cleverframe.filemanager.entity.FileInfo;
import org.cleverframe.filemanager.service.IStorageService;
import org.cleverframe.filemanager.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 各种文件上传方式 为其他模块或其他功能提供服务
 * 作者：LiZW <br/>
 * 创建时间：2017/1/17 16:57 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${base.mvcPath}/filemanager/fileupload")
public class FileUploadController extends BaseController {

    @Autowired
    @Qualifier(FilemanagerBeanNames.LocalStorageService)
//    @Qualifier(FilemanagerBeanNames.FtpStorageService)
//    @Qualifier(FilemanagerBeanNames.FastDfsStorageService)
    private IStorageService storageService;

    /**
     * clever-doc 模块上传文件
     */
    @ResponseBody
    @RequestMapping(value = "/doc/upload", method = RequestMethod.POST)
    public Map<String, Object> upload(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        AjaxMessage message = new AjaxMessage(true, "文件上传成功", null);
        List<FileInfo> fileInfoList = FileUploadUtils.upload("clever-doc", storageService, request, response, message);
        if (fileInfoList == null || fileInfoList.size() <= 0) {
            result.put("success", 0); // 0 表示上传失败，1 表示上传成功
            result.put("message", message.getFailMessage()); // 提示的信息，上传成功或上传失败及错误信息等。
            result.put("url", null); // 上传成功时才返回
        } else {
            FileInfo fileInfo = fileInfoList.get(0);
            result.put("success", 1);
            result.put("message", message.getSuccessMessage());
            //"http://localhost:8080/cleverframe/mvc/filemanager/manager/download?uuid=" + fileInfo.getUuid()
            StringBuilder sb = new StringBuilder();
            if (request.getProtocol().toUpperCase().startsWith("HTTP/")) {
                sb.append("http://");
            }
            sb.append(request.getServerName());
            if (80 != request.getServerPort()) {
                sb.append(":").append(request.getServerPort());
            }
            sb.append(request.getContextPath());
            String[] paths = request.getServletPath().split("/");
            sb.append("/").append(paths[1]); // mvc
            sb.append("/filemanager/manager/download?uuid=").append(fileInfo.getUuid());
            result.put("url", sb.toString());
        }
        return result;
    }
}
