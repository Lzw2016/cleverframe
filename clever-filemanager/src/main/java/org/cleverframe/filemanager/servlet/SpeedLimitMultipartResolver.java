package org.cleverframe.filemanager.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 自定义的处理文件上传的解析器(限制上传速度)<br>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/17 21:21 <br/>
 *
 * @see CommonsMultipartResolver
 */
public class SpeedLimitMultipartResolver extends CommonsMultipartResolver {

    /**
     * 自定义创建DiskFileItemFactory
     */
    @Override
    protected DiskFileItemFactory newFileItemFactory() {
        // diskFileItemFactory.setFileCleaningTracker(pTracker);
        return super.newFileItemFactory();
    }

    /**
     * 解析上传的文件，此方法必定会被执行
     * 重写此方法的主要目的是注册文件上传时的上传进度监听器，其他代码参考父类实现
     */
    @Override
    protected MultipartParsingResult parseRequest(HttpServletRequest request) throws MultipartException {
        String encoding = determineEncoding(request);
        FileUpload fileUpload = prepareFileUpload(encoding);

        // 注册文件上传时的上传进度监听器，并向监听器
        // fileUpload.setProgressListener(new LocalProgressListener(request.getSession()));
        try {
            List<FileItem> fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);
            return parseFileItems(fileItems, encoding);
        } catch (FileUploadBase.SizeLimitExceededException ex) {
            throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
        } catch (FileUploadException ex) {
            throw new MultipartException("Could not parse multipart servlet request", ex);
        }
    }

}
