package org.cleverframe.filemanager.service;

import org.cleverframe.filemanager.entity.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.io.Serializable;

/**
 * 上传文件存储接口<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/17 22:10 <br/>
 */
public interface IStorageService {

    /**
     * 文件打开最大速度限制 (1024 * 1024 * 1 = 1MB)
     */
    long Max_Open_Speed = 1024 * 1024;

    /**
     * 根据文件签名保存文件，实现文件秒传<br>
     *
     * @param fileName   文件名称
     * @param fileDigest 文件签名
     * @param digestType 签名类型
     * @return 保存成功返回文件信息，失败返回null
     * @throws Exception 操作失败
     */
    FileInfo lazySaveFile(String fileName, String fileDigest, Character digestType) throws Exception;

    /**
     * 保存文件，当文件较大时此方法会占用磁盘IO，因为common-fileupload会将上传文件写入硬盘的临时文件<br>
     * <p>
     * <b>注意：如果上传的文件在服务器端存在(通过文件签名判断)，就不会存储文件只会新增文件引用</b>
     *
     * @param uploadTime    文件上传所用时间
     * @param fileSource    上传文件来源(可以是系统模块名称)
     * @param multipartFile 上传的文件信息
     * @return 返回存储后的文件信息
     * @throws Exception 保存失败抛出异常
     */
    FileInfo saveFile(long uploadTime, String fileSource, MultipartFile multipartFile) throws Exception;

    /**
     * 删除服务器端的文件<br>
     *
     * @param fileInfoUuid 文件信息UUID
     * @param lazy         如果值为true表示：只删除当前文件引用；值为false表示：直接从硬盘中删除服务器端文件
     * @return 1：成功删除fileInfo和服务器端文件；2：只删除了fileInfo；3：fileInfo不存在
     */
    int deleteFile(Serializable fileInfoUuid, boolean lazy) throws Exception;

    /**
     * 判断文件在服务端是否存在<br>
     * <p>
     * <b>注意：此方法的返回值与数据库中是否存在fileInfo无关系</b>
     *
     * @param fileInfoUuid 文件信息UUID
     * @return 不存在返回null，存在返回文件信息
     */
    FileInfo isExists(Serializable fileInfoUuid) throws Exception;

    /**
     * 打开文件到OutputStream<br>
     *
     * @param fileInfoUuid 文件信息UUID
     * @param outputStream 输出流，用于打开文件
     * @return FileInfo(文件信息)。 文件不存在返回null
     * @throws Exception 操作失败
     */
    FileInfo openFile(Serializable fileInfoUuid, OutputStream outputStream) throws Exception;

    /**
     * 打开文件到OutputStream(限制打开文件速度，适用于客户端下载文件) 可以控制打开速度<br>
     * <b>注意：使用此方法会限制打开文件速度(字节/秒)</b>
     *
     * @param fileInfoUuid 文件信息UUID
     * @param outputStream 输出流，用于打开文件
     * @param maxSpeed     最大打开文件速度(字节/秒)，值小于等于0，则使用默认限制速度 {@link #Max_Open_Speed}
     * @return FileInfo(文件信息)。 文件不存在返回null
     * @throws Exception 操作失败
     */
    FileInfo openFileSpeedLimit(Serializable fileInfoUuid, OutputStream outputStream, long maxSpeed) throws Exception;
}

