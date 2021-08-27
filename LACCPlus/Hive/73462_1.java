//,temp,UpgradeTool.java,104,145,temp,PreUpgradeTool.java,124,157
//,3
public class xxx {
  public static void main(String[] args) throws Exception {
    UpgradeTool tool = new UpgradeTool();
    tool.init();
    CommandLineParser parser = new GnuParser();
    CommandLine line ;
    String outputDir = ".";
    boolean execute = false;
    try {
      line = parser.parse(tool.cmdLineOptions, args);
    } catch (ParseException e) {
      System.err.println("UpgradeTool: Parsing failed.  Reason: " + e.getLocalizedMessage());
      printAndExit(tool);
      return;
    }
    if (line.hasOption("help")) {
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("upgrade-acid", tool.cmdLineOptions);
      return;
    }
    if(line.hasOption("location")) {
      outputDir = line.getOptionValue("location");
    }
    if(line.hasOption("execute")) {
      execute = true;
    }
    LOG.info("Starting with execute=" + execute + ", location=" + outputDir);

    try {
      String hiveVer = HiveVersionInfo.getShortVersion();
      LOG.info("Using Hive Version: " + HiveVersionInfo.getVersion() + " build: " +
          HiveVersionInfo.getBuildVersion());
      if(!(hiveVer.startsWith("3.") || hiveVer.startsWith("4."))) {
        throw new IllegalStateException("postUpgrade w/execute requires Hive 3.x.  Actual: " +
            hiveVer);
      }
      tool.performUpgradeInternal(outputDir, execute);
    }
    catch(Exception ex) {
      LOG.error("UpgradeTool failed", ex);
      throw ex;
    }
  }

};