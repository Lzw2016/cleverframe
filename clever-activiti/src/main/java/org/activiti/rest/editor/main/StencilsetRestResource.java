package org.activiti.rest.editor.main;

import org.activiti.engine.ActivitiException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

/**
 * 参考 activiti-modeler<br/>
 * 改进:自定义stencilset.json文件的位置<br/>
 * 作者：LiZW <br/>
 * 创建时间：2016/12/3 20:16 <br/>
 */
@RestController
public class StencilsetRestResource {

    @Value("${stencilset}")
    private String stencilsetPath;

    @RequestMapping(value = "/editor/stencilset", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getStencilset() {
        if (StringUtils.isBlank(stencilsetPath)) {
            stencilsetPath = "stencilset.json";
        }
        InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream(stencilsetPath);
        try {
            return IOUtils.toString(stencilsetStream, "utf-8");
        } catch (Exception e) {
            throw new ActivitiException("不能加载Activiti Modeler编辑器stencilset.json文件, 路径:" + stencilsetPath, e);
        }
    }
}
