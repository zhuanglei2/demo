package com.zl.demo.common.util;

import com.google.common.base.Preconditions;
import com.zl.demo.component.SzValidator;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 校验参数合法性
 * @author zhuangl
 * @version 1.0
 * @date 2019/12/31 15:28
 */
public class CheckUtil {
    /**
     * 验证帮助
     */
    @Resource
    protected SzValidator szValidator;
    /**
     * 校验参数合法性
     *
     * @param o
     */
    protected void checkParam(Object o) {

        /** 校验参数 */
        Map<String, String> errorMsg = szValidator.vailed(o);

        Preconditions.checkArgument(errorMsg.size() == 0,
                errorMsg);
    }

}
