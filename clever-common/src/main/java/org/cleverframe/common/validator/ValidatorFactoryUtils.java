package org.cleverframe.common.validator;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;

import javax.validation.Configuration;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * 创建各种不同的Validator实现类<br/>
 * 1.Hibernate Validator<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-10 22:31 <br/>
 *
 * @see org.cleverframe.common.validator.BaseValidatorUtils
 */
public class ValidatorFactoryUtils {

    /**
     * Hibernate实现的ValidatorFactory
     */
    public static final ValidatorFactory HIBERNATE_VALIDATOR_FACTORY;

    static {
        Configuration<HibernateValidatorConfiguration> configure = Validation.byProvider(HibernateValidator.class).configure();
        HIBERNATE_VALIDATOR_FACTORY = configure.buildValidatorFactory();
    }

    /**
     * 创建Hibernate实现的Validator
     *
     * @return Hibernate实现的Validator
     */
    public static Validator getHibernateValidator() {
        return HIBERNATE_VALIDATOR_FACTORY.getValidator();
    }
}
