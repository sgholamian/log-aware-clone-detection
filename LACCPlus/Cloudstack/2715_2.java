//,temp,XenServer56NetworkUsageCommandWrapper.java,59,103,temp,VmwareResource.java,871,916
//,3
public class xxx {
    protected NetworkUsageAnswer VPCNetworkUsage(NetworkUsageCommand cmd) {
        String privateIp = cmd.getPrivateIP();
        String option = cmd.getOption();
        String publicIp = cmd.getGatewayIP();

        String args = "-l " + publicIp + " ";
        if (option.equals("get")) {
            args += "-g";
        } else if (option.equals("create")) {
            args += "-c";
            String vpcCIDR = cmd.getVpcCIDR();
            args += " -v " + vpcCIDR;
        } else if (option.equals("reset")) {
            args += "-r";
        } else if (option.equals("vpn")) {
            args += "-n";
        } else if (option.equals("remove")) {
            args += "-d";
        } else {
            return new NetworkUsageAnswer(cmd, "success", 0L, 0L);
        }

        ExecutionResult callResult = executeInVR(privateIp, "vpc_netusage.sh", args);

        if (!callResult.isSuccess()) {
            s_logger.error("Unable to execute NetworkUsage command on DomR (" + privateIp + "), domR may not be ready yet. failure due to " + callResult.getDetails());
        }

        if (option.equals("get") || option.equals("vpn")) {
            String result = callResult.getDetails();
            if (result == null || result.isEmpty()) {
                s_logger.error(" vpc network usage get returns empty ");
            }
            long[] stats = new long[2];
            if (result != null) {
                String[] splitResult = result.split(":");
                int i = 0;
                while (i < splitResult.length - 1) {
                    stats[0] += Long.parseLong(splitResult[i++]);
                    stats[1] += Long.parseLong(splitResult[i++]);
                }
                return new NetworkUsageAnswer(cmd, "success", stats[0], stats[1]);
            }
        }
        return new NetworkUsageAnswer(cmd, "success", 0L, 0L);
    }

};