package image;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/12/27 16:22 <br/>
 */
public class ThumbnailatorTest {

    // 场景一：图片尺寸不变，修改图片文件类型
    @Test
    public void test01() throws IOException {
        Thumbnails.of("F:\\image\\IMG_20131229_114806.png")
                .scale(1f)
                .outputFormat("jpg")
                .toFile("F:\\image\\output\\IMG_20131229_114806");
    }

    // 场景二：图片尺寸不变，压缩图片文件大小
    @Test
    public void test02() throws IOException {
        Thumbnails.of("F:\\image\\IMG_20131229_114806.png")
                .scale(1f)
                .outputQuality(0.25f)
                .outputFormat("jpg")
                .toFile("F:\\image\\output\\IMG_20131229_114806");
    }

    // 场景三：压缩至指定图片尺寸（例如：横400高300），不保持图片比例
    @Test
    public void test03() throws IOException {
        Thumbnails.of("F:\\image\\IMG_20131229_114806.png")
                .forceSize(400, 300)
                .toFile("F:\\image\\output\\IMG_20131229_114806");
    }

    // 场景四：压缩至指定图片尺寸（例如：横400高300），保持图片不变形，多余部分裁剪掉
    @Test
    public void test04() throws IOException {
        String imagePath = "F:\\image\\IMG_20131229_114806.jpg";
        BufferedImage image = ImageIO.read(new File(imagePath));
        Thumbnails.Builder<BufferedImage> builder;
        int imageWidth = image.getWidth();
        int imageHeitht = image.getHeight();
        if ((float) 300 / 400 != (float) imageWidth / imageHeitht) {
            if (imageWidth > imageHeitht) {
                image = Thumbnails.of(imagePath).height(300).asBufferedImage();
            } else {
                image = Thumbnails.of(imagePath).width(400).asBufferedImage();
            }
            builder = Thumbnails.of(image).sourceRegion(Positions.CENTER, 400, 300).size(400, 300);
        } else {
            builder = Thumbnails.of(image).size(400, 300);
        }
        builder.outputFormat("jpg").toFile("F:\\image\\output\\IMG_20131229_114806");
    }

    // 其他 http://blog.csdn.net/wangpeng047/article/details/17610451


    @Test
    public void test() throws IOException {
        BufferedImage originalImage = ImageIO.read(new File("F:\\IMG_20161227_163638.jpg"));

        Thumbnails.of(originalImage)
//                .scale(1f)
//                .outputQuality(0.8f)

                .size(150,150)
                .keepAspectRatio(true)

                .outputFormat("jpg")
                .toFile("F:\\thumbnailator");
    }
}
