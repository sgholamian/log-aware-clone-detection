//,temp,VirtualMachinePowerStateSyncImpl.java,61,68,temp,VirtualMachinePowerStateSyncImpl.java,53,59
//,3
public class xxx {
    @Override
    public void processHostVmStatePingReport(long hostId, Map<String, HostVmStateReportEntry> report) {
        if (s_logger.isDebugEnabled())
            s_logger.debug("Process host VM state report from ping process. host: " + hostId);

        Map<Long, VirtualMachine.PowerState> translatedInfo = convertVmStateReport(report);
        processReport(hostId, translatedInfo);
    }

};