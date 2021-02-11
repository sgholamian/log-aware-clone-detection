//,temp,ClusterMO.java,392,409,temp,HostMO.java,772,789
//,3
public class xxx {
    @Override
    public boolean createBlankVm(String vmName, String vmInternalCSName, int cpuCount, int cpuSpeedMHz, int cpuReservedMHz, boolean limitCpuUse, int memoryMB,
            int memoryReserveMB, String guestOsIdentifier, ManagedObjectReference morDs, boolean snapshotDirToParent, Pair<String, String> controllerInfo, Boolean systemVm) throws Exception {

        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - createBlankVm(). target MOR: " + _mor.getValue() + ", vmName: " + vmName + ", cpuCount: " + cpuCount + ", cpuSpeedMhz: " +
                    cpuSpeedMHz + ", cpuReservedMHz: " + cpuReservedMHz + ", limitCpu: " + limitCpuUse + ", memoryMB: " + memoryMB + ", guestOS: " + guestOsIdentifier +
                    ", datastore: " + morDs.getValue() + ", snapshotDirToParent: " + snapshotDirToParent +
                    ", controllerInfo:[" + controllerInfo.first() + "," + controllerInfo.second() + "], systemvm: " + systemVm);

        boolean result =
                HypervisorHostHelper.createBlankVm(this, vmName, vmInternalCSName, cpuCount, cpuSpeedMHz, cpuReservedMHz, limitCpuUse, memoryMB, memoryReserveMB,
                        guestOsIdentifier, morDs, snapshotDirToParent, controllerInfo, systemVm);

        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - createBlankVm() done");
        return result;
    }

};