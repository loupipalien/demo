package com.ltchen.demo.commons.cli;

import org.apache.commons.cli.*;

/**
 * @author: ltchen
 * @date: 2018/1/31
 */
public class AntExample {

    public static void main(String[] args) {
        AntExample example = new AntExample();
        // 定义解析器
        CommandLineParser parser = new DefaultParser();
        try {
            example.usage(example.defineStage());
            // 解析命令行
            CommandLine line = parser.parse(example.defineStage(), args);
        }
        catch( ParseException exp ) {
            System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
        }
    }

    public Options defineStage() {
        // 布尔选项
        Option help = new Option( "help", "print this message" );
        Option projecthelp = new Option( "projecthelp", "print project help information" );
        Option version = new Option( "version", "print the version information and exit" );
        Option quiet = new Option( "quiet", "be extra quiet" );
        Option verbose = new Option( "verbose", "be extra verbose" );
        Option debug = new Option( "debug", "print debugging information" );
        Option emacs = new Option( "emacs", "produce logging information without adornments" );
        // 参数选项
        Option logfile = Option.builder("logfile").argName("file").hasArg().desc("use given file for log").build();
        Option logger = Option.builder("logger").argName("classname").hasArg().desc("the class which it to perform logging").build();
        Option listener = Option.builder("listener").argName("classname").hasArg().desc("add an instance of class as a project listener").build();
        Option buildfile = Option.builder("buildfile").argName("file").hasArg().desc("use given buildfile").build();
        Option find = Option.builder("find").argName("file").hasArg().desc("search for buildfile towards the root of the filesystem and use it").build();
        // property 选项
        Option property  = Option.builder("D").argName("property=value").numberOfArgs(2).desc("use value for given property").build();
        // 选项
        Options options = new Options();

        options.addOption( help );
        options.addOption( projecthelp );
        options.addOption( version );
        options.addOption( quiet );
        options.addOption( verbose );
        options.addOption( debug );
        options.addOption( emacs );
        options.addOption( logfile );
        options.addOption( logger );
        options.addOption( listener );
        options.addOption( buildfile );
        options.addOption( find );
        options.addOption( property );
        return options;
    }

    public void usage(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        String syntax = "ant";
        formatter.printHelp( syntax, options);
    }
}
