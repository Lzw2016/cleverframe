package common.freemarker;

import basetest.ServiceTestBase;
import org.cleverframe.common.freemarker.FreeMarkerUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-5-26 0:29 <br/>
 */
public class FreeMarkerUtilsTest extends ServiceTestBase {

    @Test
    public void templateBindDataByTmp() {
        String templateStr = "欢迎：${name}";
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("name", "李志伟");
        String result = FreeMarkerUtils.templateBindDataByTmp(templateStr, dataModel);
        logger.info(result);

//        templateStr = "欢迎：${user}";
//        dataModel.put("user", "李志伟123456");
//        result = FreeMarkerUtils.templateBindDataByTmp(templateStr, dataModel);
//        logger.info(result);
//
//        templateStr = "欢迎：${username!\"李志伟789\"}";
//        result = FreeMarkerUtils.templateBindDataByTmp(templateStr, dataModel);
//        logger.info(result);
//
//        templateStr = "欢迎：${username}";
//        result = FreeMarkerUtils.templateBindDataByTmp(templateStr, dataModel);
//        logger.info(result);
    }
}
