//,temp,sample_5495.java,2,17,temp,sample_5496.java,2,17
//,2
public class xxx {
public void dummy_method(){
long reservedMem = capacityMemory.getReservedCapacity();
long actualTotalCpu = capacityCpu.getTotalCapacity();
float cpuOvercommitRatio = Float.parseFloat(_clusterDetailsDao.findDetail(clusterIdFinal, "cpuOvercommitRatio").getValue());
float memoryOvercommitRatio = Float.parseFloat(_clusterDetailsDao.findDetail(clusterIdFinal, "memoryOvercommitRatio").getValue());
int vmCPU = svo.getCpu() * svo.getSpeed();
long vmMem = svo.getRamSize() * 1024L * 1024L;
long actualTotalMem = capacityMemory.getTotalCapacity();
long totalMem = (long)(actualTotalMem * memoryOvercommitRatio);
long totalCpu = (long)(actualTotalCpu * cpuOvercommitRatio);
if (s_logger.isDebugEnabled()) {


log.info("hosts s actual total cpu and cpu after applying overprovisioning");
}
}

};