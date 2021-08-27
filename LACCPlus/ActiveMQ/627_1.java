//,temp,DurableSubProcessTest.java,616,622,temp,DurableSubProcessMultiRestartTest.java,334,339
//,3
public class xxx {
    public static void exit(String message, Throwable e) {
        Throwable log = new RuntimeException(message, e);
        log.printStackTrace();
        LOG.error(message, e);
        exceptions.add(e);
        fail(message);
    }

};