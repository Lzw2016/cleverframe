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

import java.util.List;

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
     * 查询所有的代码模版数据
     *
     * @return 所有的代码模版数据
     */
    public List<CodeTemplate> findAllCodeTemplate() {
        return codeTemplateDao.findAllCodeTemplate();
    }

    /**
     * 新增代码模版-分类
     * <pre>
     *      1.设置节点类型[模版分类]，设置模版引用为null
     *      2.如果不是根节点，验证父节点存在，并验证父节点类型必须是[模版分类]
     *      3.计算出节点路径 fullPath
     *      4.验证代码模版名称不存在
     *      5.保存代码模版CodeTemplate
     *      6.更新CodeTemplate fullPath属性
     * </pre>
     *
     * @param codeTemplate 代码模版信息
     * @param ajaxMessage  请求响应信息
     * @return 成功返回true，失败返回false
     */
    @Transactional(readOnly = false)
    public boolean addCodeTemplateCategory(CodeTemplate codeTemplate, AjaxMessage ajaxMessage) {
        codeTemplate.setNodeType(CodeTemplate.NodeTypeCategory);
        codeTemplate.setTemplateRef(null);

        CodeTemplate parent = null;
        if (codeTemplate.getParentId() != -1) {
            // 不是根节点，验证父节点存在
            parent = codeTemplateDao.getHibernateDao().get(codeTemplate.getParentId());
            if (parent == null) {
                ajaxMessage.setSuccess(false);
                ajaxMessage.setFailMessage("新增代码模版-分类失败,代码模版的父节点不存在，节点ID=[" + codeTemplate.getParentId() + "]");
                return false;
            }
            // 验证父节点类型必须是[模版分类]
            if (CodeTemplate.NodeTypeCode.toString().equals(parent.getCodeType())) {
                ajaxMessage.setSuccess(false);
                ajaxMessage.setFailMessage("新增代码模版-分类失败,父节点类型必须是[模版分类]");
                return false;
            }
        }

        // 设置fullPath
        if (parent == null) {
            codeTemplate.setFullPath("");
        } else {
            codeTemplate.setFullPath(parent.getFullPath());
        }
        // 根据模版名称查询模版是否存在
        if (codeTemplateDao.codeTemplateNameExists(codeTemplate.getName())) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("模版名称已存在，模版名称[" + codeTemplate.getName() + "]");
            return false;
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
     * 新增代码模版-代码模版
     * <pre>
     *      1.设置节点类型[代码模版]，设置模版引用
     *      2.如果不是根节点，验证父节点存在，并验证父节点类型必须是[模版分类]
     *      3.计算出节点路径 fullPath
     *      4.验证代码模版名称不存在
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
    public boolean addCodeTemplateCode(CodeTemplate codeTemplate, Template template, AjaxMessage ajaxMessage) {
        if (codeTemplate == null || template == null) {
            ajaxMessage.setFailMessage("新增代码模版-代码模版失败，参数“codeTemplate”、“template”不能为空");
            return false;
        }
        // 节点类型是模版节点
        codeTemplate.setNodeType(CodeTemplate.NodeTypeCode);
        codeTemplate.setTemplateRef(template.getName());

        CodeTemplate parent = null;
        if (codeTemplate.getParentId() != -1) {
            // 不是根节点，验证父节点存在
            parent = codeTemplateDao.getHibernateDao().get(codeTemplate.getParentId());
            if (parent == null) {
                ajaxMessage.setSuccess(false);
                ajaxMessage.setFailMessage("新增代码模版-代码模版失败,代码模版的父节点不存在，节点ID=[" + codeTemplate.getParentId() + "]");
                return false;
            }
            // 验证父节点类型必须是[模版分类]
            if (CodeTemplate.NodeTypeCode.toString().equals(parent.getCodeType())) {
                ajaxMessage.setSuccess(false);
                ajaxMessage.setFailMessage("新增代码模版-代码模版失败,父节点类型必须是[模版分类]");
                return false;
            }
        }

        // 设置fullPath
        if (parent == null) {
            codeTemplate.setFullPath("");
        } else {
            codeTemplate.setFullPath(parent.getFullPath());
        }
        // 根据模版名称查询模版是否存在
        if (codeTemplateDao.codeTemplateNameExists(codeTemplate.getName())) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("代码模版名称已存在，模版名称[" + codeTemplate.getName() + "]");
            return false;
        }
        if (templateService.templateNameExists(template.getName())) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("模版名称已存在，模版名称[" + template.getName() + "]");
            return false;
        }

        // 保存数据
        templateService.saveTemplate(template);
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
        codeTemplate.setNodeType(oldCodeTemplate.getNodeType());
        if (CodeTemplate.NodeTypeCode.equals(codeTemplate.getNodeType()) && template.getId() == null) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("更新代码模版,templateId不能为空");
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
        codeTemplateDao.getHibernateDao().update(codeTemplate, false, true);
        if (CodeTemplate.NodeTypeCode.equals(codeTemplate.getNodeType())) {
            templateService.updateTemplate(template);
        }
        return true;
    }

    /**
     * 直接从数据库删除模版</br>
     * <b>注意不能删除一个非空的模版类别(类别节点下存在子节点)</b>
     *
     * @param codeTemplateName 代码模版名称
     * @return 成功返回true，失败返回false
     */
    @Transactional(readOnly = false)
    public boolean delCodeTemplate(String codeTemplateName, AjaxMessage ajaxMessage) {
        CodeTemplate codeTemplate = codeTemplateDao.getByName(codeTemplateName);
        if (codeTemplate == null) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("代码模版不存在");
            return false;
        }

        if (CodeTemplate.NodeTypeCategory.equals(codeTemplate.getNodeType())) {
            long count = codeTemplateDao.countByChildNode(codeTemplate.getFullPath());
            if (count > 0) {
                ajaxMessage.setSuccess(false);
                ajaxMessage.setFailMessage("不能删除非空的模版类别,当前模版类别子节点数:" + count);
                return false;
            }
        }

        // 删除数据
        codeTemplateDao.getHibernateDao().getSession().evict(codeTemplate);
        codeTemplateDao.delByName(codeTemplate.getName());
        if (CodeTemplate.NodeTypeCode.equals(codeTemplate.getNodeType())) {
            // 代码模版删除模版
            templateService.deleteTemplate(codeTemplate.getTemplateRef());
        }
        return true;
    }
}

