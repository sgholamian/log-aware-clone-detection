//,temp,ResourceManagerImpl.java,2903,2920,temp,ResourceManagerImpl.java,2764,2784
//,3
public class xxx {
    @Override
    public HashMap<String, HashMap<String, VgpuTypesInfo>> getGPUStatistics(final HostVO host) {
        final Answer answer = _agentMgr.easySend(host.getId(), new GetGPUStatsCommand(host.getGuid(), host.getName()));
        if (answer != null && answer instanceof UnsupportedAnswer) {
            return null;
        }
        if (answer == null || !answer.getResult()) {
            final String msg = "Unable to obtain GPU stats for host " + host.getName();
            s_logger.warn(msg);
            return null;
        } else {
            // now construct the result object
            if (answer instanceof GetGPUStatsAnswer) {
                return ((GetGPUStatsAnswer)answer).getGroupDetails();
            }
        }
        return null;
    }

};