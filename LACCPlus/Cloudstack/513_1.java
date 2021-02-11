//,temp,Ovm3VirtualRoutingSupport.java,113,163,temp,VmwareResource.java,871,916
//,3
public class xxx {
    private NetworkUsageAnswer vpcNetworkUsage(NetworkUsageCommand cmd) {
        String privateIp = cmd.getPrivateIP();
        String option = cmd.getOption();
        String publicIp = cmd.getGatewayIP();

        String args = "-l " + publicIp + " ";
        if ("get".equals(option)) {
            args += "-g";
        } else if (CREATE.equals(option)) {
            args += "-c";
            String vpcCIDR = cmd.getVpcCIDR();
            args += " -v " + vpcCIDR;
        } else if ("reset".equals(option)) {
            args += "-r";
        } else if ("vpn".equals(option)) {
            args += "-n";
        } else if ("remove".equals(option)) {
            args += "-d";
        } else {
            return new NetworkUsageAnswer(cmd, SUCCESS, 0L, 0L);
        }

        ExecutionResult callResult = vrr.executeInVR(privateIp, "vpc_netusage.sh",
                args);

        if (!callResult.isSuccess()) {
            LOGGER.error("Unable to execute NetworkUsage command on DomR ("
                    + privateIp
                    + "), domR may not be ready yet. failure due to "
                    + callResult.getDetails());
        }

        if ("get".equals(option) || "vpn".equals(option)) {
            String result = callResult.getDetails();
            if (result == null || result.isEmpty()) {
                LOGGER.error(" vpc network usage get returns empty ");
            }
            long[] stats = new long[2];
            if (result != null) {
                String[] splitResult = result.split(":");
                int i = 0;
                while (i < splitResult.length - 1) {
                    stats[0] += (Long.parseLong(splitResult[i++]));
                    stats[1] += (Long.parseLong(splitResult[i++]));
                }
                return new NetworkUsageAnswer(cmd, SUCCESS, stats[0],
                        stats[1]);
            }
        }
        return new NetworkUsageAnswer(cmd, SUCCESS, 0L, 0L);
    }

};