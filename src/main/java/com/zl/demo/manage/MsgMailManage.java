package com.zl.demo.manage;

import com.zl.demo.dto.BaseResponse;
import com.zl.demo.dto.MailRequest;
import com.zl.demo.service.FileService;
import com.zl.demo.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/1/2 14:27
 */
@Component
public class MsgMailManage {

    @Autowired
    private FileService fileService;

    @Autowired
    private MailService mailService;

    public BaseResponse sendMailAsync(MailRequest req){
        BaseResponse resp = new BaseResponse();
        long start = System.currentTimeMillis();
        fileService.saveFile(req);
        mailService.sendMail(req);
        System.out.println("耗时："+(System.currentTimeMillis()-start)+"秒");
        resp.setCode("000000");
        resp.setMsg("成功");
        return resp;
    }
}
