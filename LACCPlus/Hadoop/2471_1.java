//,temp,MiniHadoopClusterManager.java,227,275,temp,MiniDFSClusterManager.java,176,222
//,3
public class xxx {
  private boolean parseArguments(String[] args) {
    Options options = makeOptions();
    CommandLine cli;
    try {
      CommandLineParser parser = new GnuParser();
      cli = parser.parse(options, args);
    } catch (ParseException e) {
      LOG.warn("options parsing failed:  " + e.getMessage());
      new HelpFormatter().printHelp("...", options);
      return false;
    }

    if (cli.hasOption("help")) {
      new HelpFormatter().printHelp("...", options);
      return false;
    }
    if (cli.getArgs().length > 0) {
      for (String arg : cli.getArgs()) {
        System.err.println("Unrecognized option: " + arg);
        new HelpFormatter().printHelp("...", options);
        return false;
      }
    }

    // MR
    noMR = cli.hasOption("nomr");
    numNodeManagers = intArgument(cli, "nodemanagers", 1);
    rmPort = intArgument(cli, "rmport", 0);
    jhsPort = intArgument(cli, "jhsport", 0);
    fs = cli.getOptionValue("namenode");

    // HDFS
    noDFS = cli.hasOption("nodfs");
    numDataNodes = intArgument(cli, "datanodes", 1);
    nnPort = intArgument(cli, "nnport", 0);
    nnHttpPort = intArgument(cli, "nnhttpport", 0);
    dfsOpts = cli.hasOption("format") ? StartupOption.FORMAT
        : StartupOption.REGULAR;

    // Runner
    writeDetails = cli.getOptionValue("writeDetails");
    writeConfig = cli.getOptionValue("writeConfig");

    // General
    conf = new JobConf();
    updateConfiguration(conf, cli.getOptionValues("D"));

    return true;
  }

};