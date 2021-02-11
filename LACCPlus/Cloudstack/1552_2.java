//,temp,VirtualMachinePowerStateSyncImpl.java,61,68,temp,VirtualMachinePowerStateSyncImpl.java,53,59
//,3
public class xxx {
    @Override
    public void processHostVmStateReport(long hostId, Map<String, HostVmStateReportEntry> report) {
            s_logger.debug("Process host VM state report. host: " + hostId);

        Map<Long, VirtualMachine.PowerState> translatedInfo = convertVmStateReport(report);
        processReport(hostId, translatedInfo);
    }

};