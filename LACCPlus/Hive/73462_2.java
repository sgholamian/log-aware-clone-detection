//,temp,UpgradeTool.java,104,145,temp,PreUpgradeTool.java,124,157
//,3
public class xxx {
  public static void main(String[] args) throws Exception {
    Options cmdLineOptions = createCommandLineOptions();
    CommandLineParser parser = new GnuParser();
    CommandLine line;
    try {
      line = parser.parse(cmdLineOptions, args);
    } catch (ParseException e) {
      System.err.println("PreUpgradeTool: Parsing failed.  Reason: " + e.getLocalizedMessage());
      printAndExit(cmdLineOptions);
      return;
    }
    if (line.hasOption("help")) {
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("upgrade-acid", cmdLineOptions);
      return;
    }
    RunOptions runOptions = RunOptions.fromCommandLine(line);
    LOG.info("Starting with " + runOptions.toString());

    try {
      String hiveVer = HiveVersionInfo.getShortVersion();
      LOG.info("Using Hive Version: " + HiveVersionInfo.getVersion() + " build: " +
              HiveVersionInfo.getBuildVersion());
      if(!hiveVer.startsWith("2.")) {
        throw new IllegalStateException("preUpgrade requires Hive 2.x.  Actual: " + hiveVer);
      }
      try (PreUpgradeTool tool = new PreUpgradeTool(runOptions)) {
        tool.prepareAcidUpgradeInternal();
      }
    } catch(Exception ex) {
      LOG.error("PreUpgradeTool failed", ex);
      throw ex;
    }
  }

};