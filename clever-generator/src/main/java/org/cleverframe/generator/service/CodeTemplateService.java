package org.cleverframe.generator.service;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.transaction.annotation.Transactional;

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
     *      1.如果不是根节点，验证父节点存在，并验证父节点类型必须是[模版分类]
     *      2.根据节点类型设置模版引用
     *      3.计算出节点路径 fullPath
     *      4.验证代码模版名称不存在
     * 模版信息Template
     *      5.验证模版名称不存在
     *      6.保存模版信息Template
     *      7.保存代码模版CodeTemplate
     *      8.更新CodeTemplate fullPath属性
     * </pre>
     *
     * @param codeTemplate 代码模版信息
     * @param template     模版对象
     * @param ajaxMessage  请求响应信息
     * @return 成功返回true，失败返回false
     */
    @Transactional(readOnly = false)
    public boolean addCodeTemplate(CodeTemplate codeTemplate, Template template, AjaxMessage ajaxMessage) {
        CodeTemplate parent = null;
        if (codeTemplate.getParentId() != -1) {
            // 不是根节点，验证父节点存在
            parent = codeTemplateDao.getHibernateDao().get(codeTemplate.getParentId());
            if (parent == null) {
                ajaxMessage.setSuccess(false);
                ajaxMessage.setFailMessage("代码模版的父节点不存在，节点ID=[" + codeTemplate.getParentId() + "]");
                return false;
            }
            // 验证父节点类型必须是[模版分类]
            if (CodeTemplate.NodeTypeCode.toString().equals(parent.getCodeType())) {
                ajaxMessage.setSuccess(false);
                ajaxMessage.setFailMessage("父节点类型必须是[模版分类]");
                return false;
            }
        }

        // 设置templateRef
        if (CodeTemplate.NodeTypeCategory.equals(codeTemplate.getNodeType())) {
            // 节点类型是分类节点
            codeTemplate.setTemplateRef(null);
        } else if (CodeTemplate.NodeTypeCode.equals(codeTemplate.getNodeType())) {
            // 节点类型是模版节点
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

        // 根据模版名称查询模版是否存在
        if (codeTemplateDao.codeTemplateNameExists(template.getName()) && templateService.templateNameExists(template.getName())) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("模版名称已存在，模版名称[" + template.getName() + "]");
            return false;
        }

        // 保存数据
        if (CodeTemplate.NodeTypeCode.equals(codeTemplate.getNodeType())) {
            templateService.saveTemplate(template);
        }
        codeTemplateDao.getHibernateDao().save(codeTemplate);

        // 更新CodeTemplate fullPath属性
        if (StringUtils.isBlank(codeTemplate.getFullPath())) {
            codeTemplate.setFullPath(codeTemplate.getId().toString());
        } else {
            codeTemplate.setFullPath(codeTemplate.getFullPath() + CodeTemplate.FULL_PATH_SPLIT + codeTemplate.getId());
        }
        codeTemplateDao.getHibernateDao().update(codeTemplate);
        return true;
    }

    /**
     * 更新代码模版,逻辑如下
     * <per>
     * 1.不能修改节点类型
     * 2.修改节点位置，验证父节点存在，并验证父节点类型必须是[模版分类]
     * 3.更新CodeTemplate fullPath属性
     * 4.更新CodeTemplate
     * </per>
     *
     * @param codeTemplate 代码模版信息
     * @param template     模版对象
     * @param ajaxMessage  请求响应信息
     * @return 成功返回true，失败返回false
     */
    @Transactional(readOnly = false)
    public boolean updateCodeTemplate(CodeTemplate codeTemplate, Template template, AjaxMessage ajaxMessage) {
        CodeTemplate oldCodeTemplate = codeTemplateDao.getHibernateDao().get(codeTemplate.getId());
        if (oldCodeTemplate == null) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("更新的数据不存在，ID=[" + codeTemplate.getName() + "]");
            return false;
        }

        // 不能修改节点类型
        if (!oldCodeTemplate.getNodeType().equals(codeTemplate.getNodeType())) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("不能修改节点类型");
            return false;
        }

        // 修改节点位置，验证父节点存在，并验证父节点类型必须是[模版分类]
        CodeTemplate parent = null;
        if (!oldCodeTemplate.getParentId().equals(codeTemplate.getParentId()) && codeTemplate.getParentId() != -1) {
            // 修改之后不是根节点,验证父节点存在
            parent = codeTemplateDao.getHibernateDao().get(codeTemplate.getParentId());
            if (parent == null) {
                ajaxMessage.setSuccess(false);
                ajaxMessage.setFailMessage("代码模版的父节点不存在，节点ID=[" + codeTemplate.getParentId() + "]");
                return false;
            }
            // 验证父节点类型必须是[模版分类]
            if (CodeTemplate.NodeTypeCode.toString().equals(parent.getCodeType())) {
                ajaxMessage.setSuccess(false);
                ajaxMessage.setFailMessage("父节点类型必须是[模版分类]");
                return false;
            }
        }

        // 更新CodeTemplate fullPath属性
        if (parent != null) {
            codeTemplate.setFullPath(parent.getFullPath() + CodeTemplate.FULL_PATH_SPLIT + codeTemplate.getId());
        }

        // 更新CodeTemplate
        codeTemplateDao.getHibernateDao().getSession().evict(oldCodeTemplate);
        codeTemplateDao.getHibernateDao().getSession().update(codeTemplate);
        templateService.updateTemplate(template);
        return true;
    }
}

