//,temp,Ovm3VirtualRoutingSupport.java,48,62,temp,VmwareResource.java,853,869
//,3
public class xxx {
    public Answer execute(NetworkUsageCommand cmd) {
        if (cmd.isForVpc()) {
            return vpcNetworkUsage(cmd);
        }
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Executing resource NetworkUsageCommand " + cmd);
        }
        if (cmd.getOption() != null && CREATE.equals(cmd.getOption())) {
            String result = networkUsage(cmd.getPrivateIP(), CREATE, null);
            return new NetworkUsageAnswer(cmd, result, 0L, 0L);
        }
        long[] stats = getNetworkStats(cmd.getPrivateIP());

        return new NetworkUsageAnswer(cmd, "", stats[0], stats[1]);
    }

};