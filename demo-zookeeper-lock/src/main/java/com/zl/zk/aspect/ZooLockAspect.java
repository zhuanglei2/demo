package com.zl.support.aspect;

import com.zl.support.annotation.LockKeyParam;
import com.zl.support.annotation.ZooLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 使用 aop 切面记录请求日志信息
 * @author zhuangl
 * @version 1.0
 * @date 2020/9/11 11:39
 */
@Aspect
@Component
@Slf4j
public class ZooLockAspect {
    private final CuratorFramework zkClient;

    private static final String KEY_PREFIX = "DISTRIBUTED_LOCK_";

    private static final String KEY_SEPARATOR = "/";

    @Autowired
    public ZooLockAspect(CuratorFramework zkClient){
        this.zkClient = zkClient;
    }

    /**
     * 切入点
     */
    @Pointcut("@annotation(com.zl.zk.annotation.ZooLock)")
    public void doLock(){

    }

    @Around("doLock()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        //参数
        Object[] args = point.getArgs();
        final ZooLock zooLock = method.getAnnotation(ZooLock.class);
        if(StringUtils.isEmpty(zooLock.key())){
            throw new RuntimeException("分布式锁键不能为空");
        }
        String lockKey = buildLockKey(zooLock,method,args);
        InterProcessMutex lock = new InterProcessMutex(zkClient,lockKey);
        try {
            // 假设上锁成功，以后拿到的都是 false
            if(lock.acquire(zooLock.timeout(),zooLock.timeUnit())){
                log.info("上锁成功");
                return point.proceed();
            } else {
                throw new RuntimeException("请勿重复提交");
            }
        }finally {
            lock.release();
        }
    }

    /**
     * 构建分布式锁的键1
     * @param lock
     * @param method
     * @param args
     * @return
     */
    private String buildLockKey(ZooLock lock, Method method, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        StringBuilder key = new StringBuilder(KEY_SEPARATOR+KEY_PREFIX+lock.key());

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        for (int i=0;i< parameterAnnotations.length; i++){
            // 循环该参数全部注解
            for (Annotation annotation : parameterAnnotations[i]) {
                // 注解 不是 @LockKeyParam
                if(annotation.annotationType().isInstance(LockKeyParam.class)){
                    continue;
                }
                // 获取所有fields
                String[] fields = ((LockKeyParam) annotation).fields();
                if(fields==null||fields.length==0){
                    if(null == args[i] || args[i].equals((Object)null)){
                        throw new RuntimeException("动态参数不能为null");
                    }
                    key.append(KEY_SEPARATOR).append(args[i]);
                }else {
                    for (String field : fields){
                        Class<?> clazz = args[i].getClass();
                        Field declareField = clazz.getDeclaredField(field);
                        declareField.setAccessible(true);
                        Object value = declareField.get(clazz);
                        key.append(KEY_SEPARATOR).append(value);
                    }
                }
            }
        }
        return key.toString();
    }
}
