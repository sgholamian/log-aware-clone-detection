//,temp,SwiftUtils.java,127,131,temp,SwiftRestClient.java,1332,1337
//,3
public class xxx {
  public static void trace(Logger log, String text, Object... args) {
    if (log.isTraceEnabled()) {
      log.trace(String.format(text, args));
    }
  }

};