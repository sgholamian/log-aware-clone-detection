//,temp,LibvirtComputingResource.java,1960,1986,temp,LibvirtComputingResource.java,1921,1944
//,3
public class xxx {
    public String configureVPCNetworkUsage(final String privateIpAddress, final String publicIp, final String option, final String vpcCIDR) {
        final Script getUsage = new Script(_routerProxyPath, s_logger);
        getUsage.add("vpc_netusage.sh");
        getUsage.add(privateIpAddress);
        getUsage.add("-l", publicIp);

        if (option.equals("get")) {
            getUsage.add("-g");
        } else if (option.equals("create")) {
            getUsage.add("-c");
            getUsage.add("-v", vpcCIDR);
        } else if (option.equals("reset")) {
            getUsage.add("-r");
        } else if (option.equals("vpn")) {
            getUsage.add("-n");
        } else if (option.equals("remove")) {
            getUsage.add("-d");
        }

        final OutputInterpreter.OneLineParser usageParser = new OutputInterpreter.OneLineParser();
        final String result = getUsage.execute(usageParser);
        if (result != null) {
            s_logger.debug("Failed to execute VPCNetworkUsage:" + result);
            return null;
        }
        return usageParser.getLine();
    }

};