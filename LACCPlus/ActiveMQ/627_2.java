//,temp,DurableSubProcessTest.java,616,622,temp,DurableSubProcessMultiRestartTest.java,334,339
//,3
public class xxx {
    public static void exit(String message, Throwable e) {
        Throwable cause = new RuntimeException(message, e);
        LOG.error(message, cause);
        exceptions.add(cause);
        fail(cause.toString());
    }

};