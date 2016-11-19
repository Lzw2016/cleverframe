package org.cleverframe.filemanager.servlet;

import com.google.common.util.concurrent.RateLimiter;
import org.apache.commons.fileupload.ProgressListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 上传文件安读取进度用于限制文件上传最大速度
 * 作者：LiZW <br/>
 * 创建时间：2016/11/18 14:03 <br/>
 */
public class SpeedLimitProgressListener implements ProgressListener {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(SpeedLimitProgressListener.class);

    /**
     * 文件上传最大速度限制 (1024 * 1024 * 1 = 1MB)
     */
    private final static long Max_Upload_Speed = 1024 * 1024;

    /**
     * 最后一次读取的字节数
     */
    private long lastBytesRead;

    /**
     * 令牌桶算法 限流
     */
    private RateLimiter rateLimiter;

    public SpeedLimitProgressListener() {
        lastBytesRead = 0;
        rateLimiter = RateLimiter.create(Max_Upload_Speed);
    }

    /**
     * @param maxUploadSpeed 最大上传速度限制(字节/秒)
     */
    public SpeedLimitProgressListener(double maxUploadSpeed) {
        lastBytesRead = 0;
        rateLimiter = RateLimiter.create(maxUploadSpeed);
    }

    /**
     * 更新上传文件的状态信息
     * pBytesRead : 已经上传到服务器的字节数
     * pContentLength : 所有文件的总大小
     * pItems : 表示第几个文件
     */
    @Override
    public void update(long pBytesRead, long pContentLength, int pItems) {
        if (rateLimiter == null) {
            return;
        }

        Long addReadBytes = pBytesRead - lastBytesRead;
        lastBytesRead = pBytesRead;
        double sleepTime = 0;
        if (addReadBytes > 0) {
            sleepTime = rateLimiter.acquire(addReadBytes.intValue());
        }
        logger.debug("已读取字节数:[{}], 本次请求字节数:[{}], 当前文件数:[{}], 当前读取=[{}], 当前休眠时间:[{}]秒", pBytesRead, pContentLength, pItems, addReadBytes, sleepTime);
    }
}
