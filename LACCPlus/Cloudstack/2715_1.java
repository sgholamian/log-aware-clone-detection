//,temp,XenServer56NetworkUsageCommandWrapper.java,59,103,temp,VmwareResource.java,871,916
//,3
public class xxx {
    protected NetworkUsageAnswer executeNetworkUsage(final NetworkUsageCommand command, final XenServer56Resource xenServer56) {
        try {
            final String option = command.getOption();
            final String publicIp = command.getGatewayIP();

            String args = " -l " + publicIp + " ";
            if (option.equals("get")) {
                args += "-g";
            } else if (option.equals("create")) {
                args += "-c";
                final String vpcCIDR = command.getVpcCIDR();
                args += " -v " + vpcCIDR;
            } else if (option.equals("reset")) {
                args += "-r";
            } else if (option.equals("vpn")) {
                args += "-n";
            } else if (option.equals("remove")) {
                args += "-d";
            } else {
                return new NetworkUsageAnswer(command, "success", 0L, 0L);
            }

            final ExecutionResult result = xenServer56.executeInVR(command.getPrivateIP(), "vpc_netusage.sh", args);
            final String detail = result.getDetails();
            if (!result.isSuccess()) {
                throw new Exception(" vpc network usage plugin call failed ");
            }
            if (option.equals("get") || option.equals("vpn")) {
                final long[] stats = new long[2];
                if (detail != null) {
                    final String[] splitResult = detail.split(":");
                    int i = 0;
                    while (i < splitResult.length - 1) {
                        stats[0] += Long.parseLong(splitResult[i++]);
                        stats[1] += Long.parseLong(splitResult[i++]);
                    }
                    return new NetworkUsageAnswer(command, "success", stats[0], stats[1]);
                }
            }
            return new NetworkUsageAnswer(command, "success", 0L, 0L);
        } catch (final Exception ex) {
            s_logger.warn("Failed to get network usage stats due to ", ex);
            return new NetworkUsageAnswer(command, ex);
        }
    }

};