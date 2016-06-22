package org.cleverframe.generator.service;

import org.cleverframe.common.service.BaseService;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.dao.TemplateDao;
import org.cleverframe.generator.GeneratorBeanNames;
import org.cleverframe.generator.dao.CodeTemplateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-22 10:50 <br/>
 */
@Service(GeneratorBeanNames.GeneratorCodeService)
public class GeneratorCodeService extends BaseService {

    @Autowired
    @Qualifier(CoreBeanNames.TemplateDao)
    private TemplateDao templateDao;

    @Autowired
    @Qualifier(GeneratorBeanNames.CodeTemplateDao)
    private CodeTemplateDao codeTemplateDao;
}
