package com.zl.demo.thread.forkJoin.task;

import org.springframework.util.CollectionUtils;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * 通过forkjoin查询指定目录下（包含子目录）的指定文件
 * @Author zhuanglei
 * @Date 2021/3/7 1:25 下午
 * @Version 1.0
 */
public class FindDirsFiles extends RecursiveAction {

    private File path;

    public FindDirsFiles(File path) {
        this.path = path;
    }

    @Override
    protected void compute() {
        List<FindDirsFiles> subTasks = new ArrayList<>();
        File[] fils = path.listFiles();

        if(fils!=null){
            for (File file: fils
                 ) {
                if(file.isDirectory()){
                    subTasks.add(new FindDirsFiles(file));
                }else {
                    if(file.getAbsolutePath().endsWith("txt")){
                        System.out.println("文件："+file.getAbsolutePath());
                    }
                }
            }

            if(!CollectionUtils.isEmpty(subTasks)){
                for(FindDirsFiles subTask:invokeAll(subTasks)){
                    subTask.join();//等待子任务执行完成
                }
            }
        }
    }


    public static void main(String[] args) {
        try {
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            FindDirsFiles task = new FindDirsFiles(new File("/"));
            System.out.println("Task is running ...");
            Thread.sleep(1);


            forkJoinPool.execute(task);
            int otherWork = 0;
            for (int i = 0; i < 100; i++) {
                otherWork = otherWork +i;
            }
            System.out.println("Main Thread done sth.....,otherWork="+otherWork);
            System.out.println("Task end");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
