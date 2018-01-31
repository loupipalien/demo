package com.ltchen.demo.commons.cli;

import org.apache.commons.cli.*;

/**
 * @author: ltchen
 * @date: 2018/1/31
 */
public class LsExample {

    public static void main(String[] args) {
        args = new String[]{"--block-size=k"};
        LsExample example = new LsExample();
        // 定义解析器
        CommandLineParser parser = new DefaultParser();
        try {
            example.usage(example.defineStage());
            // 解析命令行
            CommandLine line = parser.parse(example.defineStage(), args);
            if( line.hasOption( "block-size" ) ) {
                // 打印 block-size 的值
                System.out.println( line.getOptionValue( "block-size" ) );
            }
        }
        catch( ParseException exp ) {
            System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
        }
    }

    public Options defineStage() {
        // 选项
        Options options = new Options();
        options.addOption("a", "all", false, "do not hide entries starting with .");
        options.addOption("A", "almost-all", false, "do not list implied . and ..");
        options.addOption("b", "escape", false, "print octal escapes for nongraphic characters");
        // 无短选项的选项
        options.addOption(Option.builder().longOpt("block-size").hasArg().argName("SIZE").desc("use SIZE-byte blocks").build());
        options.addOption("B", "ignore-backups", false, "do not list implied entried ending with ~");
        options.addOption("c", false, "with -lt: sort by, and show, ctime (time of last modification of file status information) with -l:show ctime and sort by name otherwise: sort by ctime");
        options.addOption("C", false, "list entries by columns" );
        return options;
    }

    public void usage(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        String syntax = "ls";
        formatter.printHelp( syntax, options, true);
    }
}
