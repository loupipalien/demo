package com.ltchen.demo.commons.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: ltchen
 * @date: 2018/1/31
 */
public class InternationalDateApp extends DateApp{

    public static void main(String[] args) {
        // String[] commandLineArgs = {};
        String[] commandLineArgs = {"-t", "-c", "hello"};
        InternationalDateApp app = new InternationalDateApp();
        app.interrogateStage(app.parseStage(app.defineStage(), commandLineArgs));
    }

    @Override
    public Options defineStage() {
        // 定义阶段: 创建选项
        Options options = new Options();
        options.addOption("t", false, "display current time");
        options.addOption("c", true, "country code");
        return options;
    }

    @Override
    public void interrogateStage(CommandLine cmd) {
        // 询问阶段: 根据命令行中的选项执行不同的用户代码分支
        String countryCode = cmd.getOptionValue("c");
        if(countryCode != null) {
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " in " + countryCode);
        }else {
            System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        }
    }
}
