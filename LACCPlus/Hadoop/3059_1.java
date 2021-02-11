//,temp,ExceptionDiags.java,84,98,temp,KerberosAuthenticator.java,225,239
//,3
public class xxx {
  @SuppressWarnings("unchecked")
  private static <T extends IOException> T wrapWithMessage(
    T exception, String msg) {
    Class<? extends Throwable> clazz = exception.getClass();
    try {
      Constructor<? extends Throwable> ctor =
        clazz.getConstructor(String.class);
      Throwable t = ctor.newInstance(msg);
      return (T) (t.initCause(exception));
    } catch (Throwable e) {
      LOG.warn("Unable to wrap exception of type " +
               clazz + ": it has no (String) constructor", e);
      return exception;
    }
  }

};