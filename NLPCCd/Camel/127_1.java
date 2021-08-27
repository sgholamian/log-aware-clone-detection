//,temp,sample_7512.java,2,10,temp,sample_3409.java,2,11
//,3
public class xxx {
protected static void suspendNow(Consumer consumer) {
try {
ServiceHelper.suspendService(consumer);
} catch (Throwable e) {


log.info("error occurred while suspending route this exception will be ignored");
}
}

};