package com.ltchen.demo.commons.cli;

import org.apache.commons.cli.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: ltchen
 * @date: 2018/1/31
 */
public class DateApp {

    public static void main(String[] args) {
        // String[] commandLineArgs = {};
        String[] commandLineArgs = {"-t"};
        DateApp app = new DateApp();
        app.interrogateStage(app.parseStage(app.defineStage(), commandLineArgs));
    }

    public Options defineStage() {
        // 定义阶段: 创建选项
        Options options = new Options();
        options.addOption("t", false, "display current time");
        return options;
    }

    public CommandLine parseStage(Options options, String[] args) {
        // 解析阶段: 根据选项解析命令行参数
        CommandLineParser parser = new DefaultParser();
        CommandLine line = null;
        try {
            line = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return line;
    }

    public void interrogateStage(CommandLine cmd) {
        // 询问阶段: 根据命令行中的选项执行不同的用户代码分支
        if(cmd.hasOption("t")) {
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }else {
            System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        }
    }
}
