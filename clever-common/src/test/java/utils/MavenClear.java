package utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/10/18 20:45 <br/>
 */
public class MavenClear {

    private static List<File> fileList = new ArrayList<>();

    @Test
    public void clear() {
        long start = System.currentTimeMillis();
        System.out.println("开始时间：" + start);

        findFile(new File("D:\\ToolsSoftware\\Maven\\.m2"));

        long end = System.currentTimeMillis();
        System.out.println("完成，用时：" + (end - start));
        System.out.println("文件数量" + fileList.size());

        //noinspection Convert2streamapi
        for (File file : fileList) {
            deleteErrorFile(file);
        }
    }

    private void findFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            assert files != null;
            for (File f : files) {
                findFile(f);
            }
        } else if (file.isFile()) {
            fileList.add(file);
        }
    }

    private void deleteErrorFile(File file) {
        String fileName = FilenameUtils.getExtension(file.toString());
        if (!"lastUpdated".equals(fileName)) {
            return;
        }

        try {
            String text = FileUtils.readFileToString(file);
            if (StringUtils.contains(text, "Could not transfer")) {
                System.out.println(text);
                System.out.println(file.toString());
                System.out.println("删除文件：" + file.delete());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
