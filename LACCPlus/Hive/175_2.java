//,temp,LlapServiceCommandLine.java,324,340,temp,LlapStatusServiceCommandLine.java,161,177
//,3
public class xxx {
  public static LlapStatusServiceCommandLine parseArguments(String[] args) {
    LlapStatusServiceCommandLine cl = null;
    try {
      cl = new LlapStatusServiceCommandLine(args);
    } catch (Exception e) {
      LOGGER.error("Parsing the command line arguments failed", e);
      printUsage();
      System.exit(ExitCode.INCORRECT_USAGE.getCode());
    }

    if (cl.isHelp()) {
      printUsage();
      System.exit(0);
    }

    return cl;
  }

};