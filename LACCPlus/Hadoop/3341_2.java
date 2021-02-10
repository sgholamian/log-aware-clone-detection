//,temp,ReflectionUtils.java,238,259,temp,ReflectionUtils.java,209,230
//,2
public class xxx {
  public static void logThreadInfo(Log log,
                                   String title,
                                   long minInterval) {
    boolean dumpStack = false;
    if (log.isInfoEnabled()) {
      synchronized (ReflectionUtils.class) {
        long now = Time.monotonicNow();
        if (now - previousLogTime >= minInterval * 1000) {
          previousLogTime = now;
          dumpStack = true;
        }
      }
      if (dumpStack) {
        try {
          ByteArrayOutputStream buffer = new ByteArrayOutputStream();
          printThreadInfo(new PrintStream(buffer, false, "UTF-8"), title);
          log.info(buffer.toString(Charset.defaultCharset().name()));
        } catch (UnsupportedEncodingException ignored) {
        }
      }
    }
  }

};