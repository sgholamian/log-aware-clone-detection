//,temp,sample_4956.java,2,11,temp,sample_4954.java,2,11
//,3
public class xxx {
public static boolean suspendService(Object service) throws Exception {
if (service instanceof Suspendable && service instanceof SuspendableService) {
SuspendableService ss = (SuspendableService) service;
if (!ss.isSuspended()) {


log.info("suspending service");
}
}
}

};