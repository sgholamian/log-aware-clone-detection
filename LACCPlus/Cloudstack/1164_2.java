//,temp,Ovm3HypervisorSupport.java,694,713,temp,OvmResourceBase.java,675,690
//,3
public class xxx {
    protected Answer execute(GetHostStatsCommand cmd) {
        try {
            Map<String, String> res = OvmHost.getPerformanceStats(_conn, _publicNetworkName);
            Double cpuUtil = Double.parseDouble(res.get("cpuUtil"));
            Double rxBytes = Double.parseDouble(res.get("rxBytes"));
            Double txBytes = Double.parseDouble(res.get("txBytes"));
            Double totalMemory = Double.parseDouble(res.get("totalMemory"));
            Double freeMemory = Double.parseDouble(res.get("freeMemory"));
            HostStatsEntry hostStats = new HostStatsEntry(cmd.getHostId(), cpuUtil, rxBytes, txBytes, "host", totalMemory, freeMemory, 0, 0);
            return new GetHostStatsAnswer(cmd, hostStats);
        } catch (Exception e) {
            s_logger.debug("Get host stats of " + cmd.getHostName() + " failed", e);
            return new Answer(cmd, false, e.getMessage());
        }

    }

};