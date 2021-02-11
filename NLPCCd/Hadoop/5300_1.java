//,temp,sample_8253.java,2,13,temp,sample_9205.java,2,14
//,3
public class xxx {
private void stop(int numOfServicesStarted, boolean stopOnlyStartedServices) {
Exception firstException = null;
List<Service> services = getServices();
for (int i = numOfServicesStarted - 1; i >= 0; i--) {
Service service = services.get(i);
if (LOG.isDebugEnabled()) {


log.info("stopping service");
}
}
}

};