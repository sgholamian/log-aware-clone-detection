//,temp,Ovm3VirtualRoutingSupport.java,48,62,temp,VmwareResource.java,853,869
//,3
public class xxx {
    protected Answer execute(NetworkUsageCommand cmd) {
        if (cmd.isForVpc()) {
            return VPCNetworkUsage(cmd);
        }
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource NetworkUsageCommand " + _gson.toJson(cmd));
        }
        if (cmd.getOption() != null && cmd.getOption().equals("create")) {
            String result = networkUsage(cmd.getPrivateIP(), "create", null);
            NetworkUsageAnswer answer = new NetworkUsageAnswer(cmd, result, 0L, 0L);
            return answer;
        }
        long[] stats = getNetworkStats(cmd.getPrivateIP());

        NetworkUsageAnswer answer = new NetworkUsageAnswer(cmd, "", stats[0], stats[1]);
        return answer;
    }

};