//,temp,Ovm3HypervisorSupport.java,694,713,temp,OvmResourceBase.java,675,690
//,3
public class xxx {
    public Answer execute(GetHostStatsCommand cmd) {
        try {
            CloudstackPlugin cSp = new CloudstackPlugin(c);
            Map<String, String> stats = cSp.ovsDom0Stats(config
                    .getAgentPublicNetworkName());
            Double cpuUtil = Double.parseDouble(stats.get("cpu"));
            Double rxBytes = Double.parseDouble(stats.get("rx"));
            Double txBytes = Double.parseDouble(stats.get("tx"));
            Double totalMemory = Double.parseDouble(stats.get("total"));
            Double freeMemory = Double.parseDouble(stats.get("free"));
            HostStatsEntry hostStats = new HostStatsEntry(cmd.getHostId(),
                    cpuUtil, rxBytes, txBytes, "host", totalMemory, freeMemory,
                    0, 0);
            return new GetHostStatsAnswer(cmd, hostStats);
        } catch (Exception e) {
            LOGGER.debug("Unable to get host stats for: " + cmd.getHostName(),
                    e);
            return new Answer(cmd, false, e.getMessage());
        }
    }

};