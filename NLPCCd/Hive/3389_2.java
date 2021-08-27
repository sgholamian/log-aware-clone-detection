//,temp,sample_5012.java,2,19,temp,sample_3846.java,2,19
//,2
public class xxx {
public void dummy_method(){
tableContainer.putRow((Writable) kvReader.getCurrentKey(), (Writable) kvReader.getCurrentValue());
numEntries++;
if (doMemCheck && (numEntries % memoryMonitorInfo.getMemoryCheckInterval() == 0)) {
final long estMemUsage = tableContainer.getEstimatedMemorySize();
if (estMemUsage > effectiveThreshold) {
String msg = "Hash table loading exceeded memory limits for input: " + inputName + " numEntries: " + numEntries + " estimatedMemoryUsage: " + estMemUsage + " effectiveThreshold: " + effectiveThreshold + " memoryMonitorInfo: " + memoryMonitorInfo;
LOG.error(msg);
throw new MapJoinMemoryExhaustionError(msg);
} else {
if (LOG.isInfoEnabled()) {


log.info("checking hash table loader memory usage for input numentries estimatedmemoryusage effectivethreshold");
}
}
}
}

};