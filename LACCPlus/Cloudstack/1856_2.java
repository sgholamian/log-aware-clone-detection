//,temp,VmwareResource.java,853,869,temp,HypervDirectConnectResource.java,2045,2061
//,3
public class xxx {
    protected Answer execute(final NetworkUsageCommand cmd) {
        if (cmd.isForVpc()) {
            //return VPCNetworkUsage(cmd);
        }
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource NetworkUsageCommand " + s_gson.toJson(cmd));
        }
        if (cmd.getOption() != null && cmd.getOption().equals("create")) {
            networkUsage(cmd.getPrivateIP(), "create", null);
            final NetworkUsageAnswer answer = new NetworkUsageAnswer(cmd, "true", 0L, 0L);
            return answer;
        }
        final long[] stats = getNetworkStats(cmd.getPrivateIP());

        final NetworkUsageAnswer answer = new NetworkUsageAnswer(cmd, "", stats[0], stats[1]);
        return answer;
    }

};