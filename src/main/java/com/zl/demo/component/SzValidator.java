package com.zl.demo.component;

import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @className ValidatorHandle
 * @Description 参数校验帮助类
 * @Author shiwang
 * @Date 2018-06-21 15:53
 */
@Component
public class SzValidator {

    @Resource
    private LocalValidatorFactoryBean validator;

    /**
     * 参数验证
     *
     * @param request
     */
    public Map<String, String> vailed(Object request) {

        Map<String, String> errorMsg = new HashMap<>();
        Set<ConstraintViolation<Object>> validateResult = validator.getValidator().validate(request, new Class<?>[] { Default.class });
        if (validateResult != null && validateResult.size() > 0) {
            for (ConstraintViolation<Object> violation : validateResult) {
                errorMsg.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
        }
        return errorMsg;
    }


}
