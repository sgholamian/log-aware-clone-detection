//,temp,SwiftUtils.java,113,117,temp,RandomTextDataGenerator.java,124,131
//,3
public class xxx {
  public static void debugEx(Logger log, String text, Exception ex) {
    if (log.isDebugEnabled()) {
      log.debug(text + ex, ex);
    }
  }

};