package org.cleverframe.generator.format;

import de.hunsicker.jalopy.Jalopy;
import org.cleverframe.common.utils.IDCreateUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.util.Map;

/**
 * Java代码格式化使用Jalopy实现<br/>
 * TODO 可以使使用Eclipse JDT实现<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-21 0:04 <br/>
 */
@SuppressWarnings("unchecked")
public class JavaFormatUtils {
    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(JavaFormatUtils.class);

    /**
     * 转换使用的临时文件路径
     */
    private final static String TmpFilePath = System.getProperty("java.io.tmpdir");

    /**
     * Eclipse JDT 默认配置
     */
    private final static Map EclipseDefaultSettings;

    static {
        try {
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource path = resourceLoader.getResource("classpath:/jalopy/jalopy.xml");
            File file = path.getFile();
            logger.debug("### 加载Jalopy配置文件,path={}", file.getPath());
            // 载入 Jalopy 格式化配置文件
            Jalopy.setConvention(file);
        } catch (Throwable e) {
            logger.error("Jalopy 初始化失败", e);
        }

        EclipseDefaultSettings = DefaultCodeFormatterConstants.getEclipseDefaultSettings();
        EclipseDefaultSettings.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_6);
        EclipseDefaultSettings.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_6);
        EclipseDefaultSettings.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_6);
        EclipseDefaultSettings.put(DefaultCodeFormatterConstants.FORMATTER_LINE_SPLIT, "160");
        EclipseDefaultSettings.put(DefaultCodeFormatterConstants.FORMATTER_TAB_CHAR, JavaCore.SPACE);
    }

    /**
     * 使用Jsoup格式化java代码
     *
     * @param javaCode java代码
     * @return 失败返回 ""
     */
    public static String javaFormatByJalopy(String javaCode) {
        String result = "";
        StringBuffer stringBuffer = new StringBuffer();
        try {
            Jalopy jalopy = new Jalopy();
            String tempPath = TmpFilePath + IDCreateUtils.uuid();
            jalopy.setInput(javaCode, tempPath);
            // jalopy.setFileFormat(FileFormat.DEFAULT);
            jalopy.setOutput(stringBuffer);
            jalopy.format();
            if (jalopy.getState() == Jalopy.State.OK || jalopy.getState() == Jalopy.State.PARSED) {
                result = stringBuffer.toString();// 格式化成功
            } else if (jalopy.getState() == Jalopy.State.WARN) {
                result = javaCode;// 格式化成功，但是有警告
            } else if (jalopy.getState() == Jalopy.State.ERROR) {
                result = javaCode; // 格式化失败
                logger.warn("### javaFormatByJalopy格式化失败");
            }
        } catch (Throwable e) {
            logger.error("Java代码格式化失败[Jalopy]", e);
        }
        return result;
    }

    /**
     * 使用Eclipse JDT格式化java代码
     *
     * @param javaCode java代码
     * @return 失败返回 ""
     */
    @SuppressWarnings("unchecked")
    public static String javaFormatByJDT(String javaCode) {
        String result = "";
        IDocument document;
        CodeFormatter codeFormatter;
        TextEdit textEdit;
        try {
            codeFormatter = ToolFactory.createCodeFormatter(EclipseDefaultSettings);
            textEdit = codeFormatter.format(CodeFormatter.K_UNKNOWN, javaCode, 0, javaCode.length(), 0, null);
            if (textEdit != null) {
                document = new Document(javaCode);
                textEdit.apply(document);
                result = document.get();
            }
        } catch (Throwable e) {
            logger.error("Java代码格式化失败[Eclipse JDT]", e);
        }
        return result;
    }
}
