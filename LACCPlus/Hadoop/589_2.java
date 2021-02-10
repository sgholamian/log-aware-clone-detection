//,temp,OptionsParser.java,203,217,temp,OptionsParser.java,181,195
//,2
public class xxx {
  private static void parseSizeLimit(CommandLine command) {
    if (command.hasOption(DistCpOptionSwitch.SIZE_LIMIT.getSwitch())) {
      String sizeLimitString = getVal(command,
                              DistCpOptionSwitch.SIZE_LIMIT.getSwitch().trim());
      try {
        Long.parseLong(sizeLimitString);
      }
      catch (NumberFormatException e) {
        throw new IllegalArgumentException("Size-limit is invalid: "
                                            + sizeLimitString, e);
      }
      LOG.warn(DistCpOptionSwitch.SIZE_LIMIT.getSwitch() + " is a deprecated" +
              " option. Ignoring.");
    }
  }

};