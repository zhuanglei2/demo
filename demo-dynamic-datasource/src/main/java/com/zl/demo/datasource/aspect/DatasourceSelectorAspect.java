package com.zl.demo.datasource.aspect;

import com.zl.demo.datasource.annotation.DefaultDataSource;
import com.zl.demo.datasource.data.DatasourceConfigContextHolder;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/11 20:31
 */
@Aspect
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DatasourceSelectorAspect {

    @Pointcut("execution(public * com.zl.demo)")
    public void datasourcePoincut(){

    }

    @Before("datasourcePoincut()")
    public void doBefore(JoinPoint joinpoint){
        MethodSignature methodSignature = (MethodSignature) joinpoint.getSignature();
        Method method = methodSignature.getMethod();
        //排除不可切换数据源的方法
        DefaultDataSource annotation = method.getAnnotation(DefaultDataSource.class);
        if(annotation!=null){
            DatasourceConfigContextHolder.setDefaultDatasource();
        }else {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
            HttpServletRequest request = attributes.getRequest();
            String configIdInHeader = request.getHeader("Datasource-Config-Id");
            if(StringUtils.hasText(configIdInHeader)){
                DatasourceConfigContextHolder.setCurrentDatasourceConfig(Long.parseLong(configIdInHeader));
            }else {
                DatasourceConfigContextHolder.setDefaultDatasource();
            }
        }
    }

    @AfterReturning("datasourcePoincut()")
    public void doAfter(){
        DatasourceConfigContextHolder.setDefaultDatasource();
    }
}
