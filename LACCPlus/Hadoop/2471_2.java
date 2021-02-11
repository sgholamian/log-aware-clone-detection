//,temp,MiniHadoopClusterManager.java,227,275,temp,MiniDFSClusterManager.java,176,222
//,3
public class xxx {
  private boolean parseArguments(String[] args) {
    Options options = makeOptions();
    CommandLine cli;
    try {
      CommandLineParser parser = new GnuParser();
      cli = parser.parse(options, args);
    } catch(ParseException e) {
      LOG.warn("options parsing failed", e);
      new HelpFormatter().printHelp("...", options);
      return false;
    }

    if (cli.hasOption("help")) {
      new HelpFormatter().printHelp("...", options);
      return false;
    }
    
    if (cli.getArgs().length > 0) {
      for (String arg : cli.getArgs()) {
        LOG.error("Unrecognized option: {}", arg);
        new HelpFormatter().printHelp("...", options);
        return false;
      }
    }

    // HDFS
    numDataNodes = intArgument(cli, "datanodes", 1);
    nameNodePort = intArgument(cli, "nnport", 0);
    nameNodeHttpPort = intArgument(cli, "httpport", 0);
    if (cli.hasOption("format")) {
      dfsOpts = StartupOption.FORMAT;
      format = true;
    } else {
      dfsOpts = StartupOption.REGULAR;
      format = false;
    }

    // Runner
    writeDetails = cli.getOptionValue("writeDetails");
    writeConfig = cli.getOptionValue("writeConfig");

    // General
    conf = new HdfsConfiguration();
    updateConfiguration(conf, cli.getOptionValues("D"));

    return true;
  }

};