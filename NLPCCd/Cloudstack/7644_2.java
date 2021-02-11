//,temp,sample_6073.java,2,17,temp,sample_6074.java,2,17
//,2
public class xxx {
public void dummy_method(){
ClusterDetailsVO cluster_detail_ram = _clusterDetailsDao.findDetail(cluster_id, "memoryOvercommitRatio");
Float cpuOvercommitRatio = Float.parseFloat(cluster_detail_cpu.getValue());
Float memoryOvercommitRatio = Float.parseFloat(cluster_detail_ram.getValue());
boolean hostHasCpuCapability, hostHasCapacity = false;
hostHasCpuCapability = _capacityMgr.checkIfHostHasCpuCapability(host.getId(), offering.getCpu(), offering.getSpeed());
if (hostHasCpuCapability) {
hostHasCapacity = _capacityMgr.checkIfHostHasCapacity(host.getId(), cpu_requested, ram_requested, true, cpuOvercommitRatio, memoryOvercommitRatio, true);
if (!hostHasCapacity) hostHasCapacity = _capacityMgr.checkIfHostHasCapacity(host.getId(), cpu_requested, ram_requested, false, cpuOvercommitRatio, memoryOvercommitRatio, true);
}
if (hostHasCapacity && hostHasCpuCapability) {


log.info("now checking for suitable pools under zone pod cluster");
}
}

};