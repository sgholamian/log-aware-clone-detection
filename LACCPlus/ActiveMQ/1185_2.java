//,temp,DurableSubProcessWithRestartTest.java,730,735,temp,DurableSubDelayedUnsubscribeTest.java,703,708
//,2
public class xxx {
    public static void exit(String message, Throwable e) {
        Throwable cause = new RuntimeException(message, e);
        LOG.error(message, cause);
        exceptions.add(cause);
        fail(cause.toString());
    }

};