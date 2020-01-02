package com.zl.demo.service.impl;

import com.zl.demo.dto.MailRequest;
import com.zl.demo.service.FileService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/1/2 14:31
 */
@Service
public class FileServiceImpl implements FileService {

    /**
     * 异步写文件
     * @param req
     */
    @Override
    @Async
    public void saveFile(MailRequest req) {
        long start = System.currentTimeMillis();
        String content = req.getContent();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        File file = new File("E://test_appendFile.txt");
        try{
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
            System.out.println("文件写入成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("耗时："+(System.currentTimeMillis()-start)+"秒");
    }
}
