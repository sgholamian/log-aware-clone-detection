//,temp,sample_7320.java,2,16,temp,sample_2313.java,2,13
//,3
public class xxx {
private static <T extends IOException> T wrapWithMessage( T exception, String msg) {
Class<? extends Throwable> clazz = exception.getClass();
try {
Constructor<? extends Throwable> ctor = clazz.getConstructor(String.class);
Throwable t = ctor.newInstance(msg);
return (T) (t.initCause(exception));
} catch (Throwable e) {


log.info("unable to wrap exception of type it has no string constructor");
}
}

};