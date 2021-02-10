//,temp,KerberosAuthenticator.java,225,239,temp,IOUtils.java,501,515
//,3
public class xxx {
  @VisibleForTesting
   static <T extends Exception> T wrapExceptionWithMessage(
      T exception, String msg) {
    Class<? extends Throwable> exceptionClass = exception.getClass();
    try {
      Constructor<? extends Throwable> ctor = exceptionClass
          .getConstructor(String.class);
      Throwable t = ctor.newInstance(msg);
      return (T) (t.initCause(exception));
    } catch (Throwable e) {
      LOG.debug("Unable to wrap exception of type {}, it has "
          + "no (String) constructor.", exceptionClass, e);
      return exception;
    }
  }

};