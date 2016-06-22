package org.cleverframe.generator.service;

import org.cleverframe.common.service.BaseService;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.entity.Template;
import org.cleverframe.core.service.ITemplateService;
import org.cleverframe.generator.GeneratorBeanNames;
import org.cleverframe.generator.dao.CodeTemplateDao;
import org.cleverframe.generator.entity.CodeTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-21 22:00 <br/>
 */
@Service(GeneratorBeanNames.CodeTemplateService)
public class CodeTemplateService extends BaseService {

    @Autowired
    @Qualifier(GeneratorBeanNames.CodeTemplateDao)
    private CodeTemplateDao codeTemplateDao;

    @Autowired
    @Qualifier(CoreBeanNames.EhCacheTemplateService)
    private ITemplateService templateService;

    /**
     * 增加代码模版,逻辑如下
     * <pre>
     * 代码模版CodeTemplate
     *      1.如果不是根节点
     *      2.计算出节点路径 fullPath
     * 模版信息Template
     *      1.根据模版名称查询模版是否存在,若模版存在就更新模版信息
     *      2.若模版不存在新增模版
     * </pre>
     *
     * @param codeTemplate 代码模版信息
     * @param template     模版对象
     * @param ajaxMessage  请求响应信息
     * @return 成功返回true，失败返回false
     */
    public boolean addCodeTemplate(CodeTemplate codeTemplate, Template template, AjaxMessage ajaxMessage) {
        CodeTemplate parent = null;
        if (codeTemplate.getParentId() != -1) {
            parent = codeTemplateDao.getHibernateDao().get(codeTemplate.getParentId());
            if (parent == null) {
                ajaxMessage.setSuccess(false);
                ajaxMessage.setFailMessage("代码模版的父节点不存在，节点ID=[" + codeTemplate.getParentId() + "]");
                return false;
            }
        }

        // 设置templateRef
        if (CodeTemplate.NodeTypeCategory.equals(codeTemplate.getNodeType())) {
            codeTemplate.setTemplateRef(null);
        } else if (CodeTemplate.NodeTypeCode.equals(codeTemplate.getNodeType())) {
            codeTemplate.setTemplateRef(template.getName());
        } else {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("代码模版节点类型未知[" + codeTemplate.getNodeType() + "]");
            return false;
        }

        // 设置fullPath
        if (parent == null) {
            codeTemplate.setFullPath("");
        } else {
            codeTemplate.setFullPath(parent.getFullPath());
        }

        // TODO 根据模版名称查询模版是否存在

        return true;
    }
}

