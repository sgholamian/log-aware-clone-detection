//,temp,LlapServiceCommandLine.java,324,340,temp,LlapStatusServiceCommandLine.java,161,177
//,3
public class xxx {
  static LlapServiceCommandLine parseArguments(String[] args) {
    LlapServiceCommandLine cl = null;
    try {
      cl = new LlapServiceCommandLine(args);
    } catch (Exception e) {
      LOG.error("Parsing the command line arguments failed", e);
      printUsage();
      System.exit(1);
    }

    if (cl.isHelp) {
      printUsage();
      System.exit(0);
    }

    return cl;
  }

};