package org.cleverframe.filemanager.utils;

import org.cleverframe.common.time.DateTimeUtils;

import java.io.File;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/17 22:29 <br/>
 */
public class StoragePathUtils {

    /**
     * 根据当前时间生成文件存储路径，生成策略：basePath/yyyy/yyyy-MM/yyyy-MM-dd <br>
     *
     * @param basePath 存储文件基路径
     * @return 返回存储文件完整路径，类似：basePath + /yyyy/yyyy-MM/yyyy-MM-dd
     */
    public static String createFilePathByDate(String basePath) {
        long dateTime = System.currentTimeMillis();
        String dateStr = DateTimeUtils.getDate(dateTime, "yyyy-MM-dd");
        String[] array = dateStr.split("-");
        return basePath +
                File.separator +
                array[0] +
                File.separator +
                array[0] + "-" + array[1] +
                File.separator +
                dateStr;
    }
}


