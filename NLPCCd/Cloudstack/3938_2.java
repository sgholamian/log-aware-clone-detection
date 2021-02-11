//,temp,sample_6361.java,2,7,temp,sample_3562.java,2,7
//,2
public class xxx {
public boolean createBlankVm(String vmName, String vmInternalCSName, int cpuCount, int cpuSpeedMHz, int cpuReservedMHz, boolean limitCpuUse, int memoryMB, int memoryReserveMB, String guestOsIdentifier, ManagedObjectReference morDs, boolean snapshotDirToParent, Pair<String, String> controllerInfo, Boolean systemVm) throws Exception {
boolean result = HypervisorHostHelper.createBlankVm(this, vmName, vmInternalCSName, cpuCount, cpuSpeedMHz, cpuReservedMHz, limitCpuUse, memoryMB, memoryReserveMB, guestOsIdentifier, morDs, snapshotDirToParent, controllerInfo, systemVm);


log.info("vcenter api trace createblankvm done");
}

};