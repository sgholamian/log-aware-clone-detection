//,temp,SwiftUtils.java,101,105,temp,RandomTextDataGenerator.java,105,112
//,3
public class xxx {
  public static void debug(Logger log, String text, Object... args) {
    if (log.isDebugEnabled()) {
      log.debug(String.format(text, args));
    }
  }

};