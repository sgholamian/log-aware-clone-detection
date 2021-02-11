//,temp,sample_5501.java,2,17,temp,sample_5502.java,2,17
//,2
public class xxx {
public void dummy_method(){
long reservedMem = capacityMem.getReservedCapacity();
long actualTotalCpu = capacityCpu.getTotalCapacity();
long actualTotalMem = capacityMem.getTotalCapacity();
long totalCpu = (long)(actualTotalCpu * cpuOvercommitRatio);
long totalMem = (long)(actualTotalMem * memoryOvercommitRatio);
if (s_logger.isDebugEnabled()) {
}
long freeCpu = totalCpu - (reservedCpu + usedCpu);
long freeMem = totalMem - (reservedMem + usedMem);
if (s_logger.isDebugEnabled()) {


log.info("we are allocating vm increasing the used capacity of this host");
}
}

};