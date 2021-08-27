//,temp,sample_3842.java,2,19,temp,sample_5009.java,2,19
//,2
public class xxx {
public void dummy_method(){
boolean doMemCheck = false;
long effectiveThreshold = 0;
if (memoryMonitorInfo != null) {
effectiveThreshold = memoryMonitorInfo.getEffectiveThreshold(desc.getMaxMemoryAvailable());
if (!LlapDaemonInfo.INSTANCE.isLlap()) {
memoryMonitorInfo.setLlap(false);
}
if (memoryMonitorInfo.doMemoryMonitoring()) {
doMemCheck = true;
if (LOG.isInfoEnabled()) {


log.info("memory monitoring for hash table loader enabled");
}
}
}
}

};