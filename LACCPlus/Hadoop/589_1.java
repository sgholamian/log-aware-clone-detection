//,temp,OptionsParser.java,203,217,temp,OptionsParser.java,181,195
//,2
public class xxx {
  private static void parseFileLimit(CommandLine command) {
    if (command.hasOption(DistCpOptionSwitch.FILE_LIMIT.getSwitch())) {
      String fileLimitString = getVal(command,
                              DistCpOptionSwitch.FILE_LIMIT.getSwitch().trim());
      try {
        Integer.parseInt(fileLimitString);
      }
      catch (NumberFormatException e) {
        throw new IllegalArgumentException("File-limit is invalid: "
                                            + fileLimitString, e);
      }
      LOG.warn(DistCpOptionSwitch.FILE_LIMIT.getSwitch() + " is a deprecated" +
          " option. Ignoring.");
    }
  }

};