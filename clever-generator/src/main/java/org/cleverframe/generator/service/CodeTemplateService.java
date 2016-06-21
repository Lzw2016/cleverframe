package org.cleverframe.generator.service;

import org.cleverframe.common.service.BaseService;
import org.cleverframe.generator.GeneratorBeanNames;
import org.cleverframe.generator.dao.CodeTemplateDao;
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
}
