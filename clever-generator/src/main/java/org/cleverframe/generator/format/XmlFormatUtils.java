package org.cleverframe.generator.format;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * xml格式化，使用Dom4j、XmlApis实现<br/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-21 0:08 <br/>
 */
public class XmlFormatUtils {
    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(XmlFormatUtils.class);

    /**
     * 使用Dom4j格式化XML数据
     *
     * @param xml      XML字符串
     * @param encoding 设置格式化之后数据的编码，可为空，默认是：UTF-8
     * @return 成功返回格式化之后的数据，失败返回 ""
     */
    public static String xmlFormatByDom4j(String xml, String encoding) {
        if (StringUtils.isBlank(encoding)) {
            encoding = "UTF-8";
        }
        String formatXml = "";
        try {
            SAXReader reader = new SAXReader();
            StringReader in = new StringReader(xml);
            Document doc = reader.read(in);
            OutputFormat formater = OutputFormat.createPrettyPrint();
            formater.setEncoding(encoding);
            // false:<tagName/> | true:<tagName></tagName>
            formater.setExpandEmptyElements(false);
            // 缩进字符
            // formater.setIndent(" ");
            // 每个层级使用几个缩进字符
            formater.setIndentSize(4);
            // 设置换行字符串
            formater.setLineSeparator("\r\n");
            // 设置XML的申明“<?xml version="1.0" encoding="UTF-8"?>”之后会不会有换行
            formater.setNewLineAfterDeclaration(true);
            // 设置是否不打印XML的申明
            formater.setSuppressDeclaration(false);
            // 是否换行
            formater.setNewlines(true);
            // 是否忽略XML的申明中的“encoding="UTF-8"”申明
            formater.setOmitEncoding(false);
            // formater.setTrimText(true);
            // formater.setXHTML(true);
            // formater.setNewLineAfterNTags(0);
            // formater.setTrimText(false);
            StringWriter out = new StringWriter();
            XMLWriter writer = new XMLWriter(out, formater);
            writer.write(doc);
            writer.close();
            out.close();
            formatXml = out.toString();
        } catch (Throwable e) {
            logger.error("XML格式化失败[Dom4j]", e);
        }
        return formatXml;
    }

    /**
     * 使用XmlApis格式化XML字符串<br>
     *
     * @param xml XML字符串
     * @return 失败返回 ""
     */
    public static String xmlFormatByXmlApis(String xml) {
        String formatXml = "";
        try {
            InputSource src = new InputSource(new StringReader(xml));
            Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src).getDocumentElement();
            Boolean keepDeclaration = xml.startsWith("<?xml");
            // 可能需要调用 System.setProperty(DOMImplementationRegistry.PROPERTY, "com.sun.org.apache.xerces.internal.dom.DOMImplementationSourceImpl");
            DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
            DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
            LSSerializer writer = impl.createLSSerializer();
            writer.getDomConfig().setParameter("format-pretty-print", true);
            writer.getDomConfig().setParameter("xml-declaration", keepDeclaration);
            formatXml = writer.writeToString(document);
        } catch (Exception e) {
            logger.error("XML格式化失败[XmlApis]", e);
        }
        return formatXml;
    }
}
