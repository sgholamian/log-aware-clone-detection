//,temp,F5BigIpResource.java,289,326,temp,NetscalerResource.java,445,484
//,3
public class xxx {
    private synchronized Answer execute(final IpAssocCommand cmd, final int numRetries) {
        if (_isSdx) {
            return Answer.createUnsupportedCommandAnswer(cmd);
        }

        final String[] results = new String[cmd.getIpAddresses().length];
        int i = 0;
        try {
            final IpAddressTO[] ips = cmd.getIpAddresses();
            for (final IpAddressTO ip : ips) {
                final long guestVlanTag = Long.parseLong(ip.getBroadcastUri());
                final String vlanSelfIp = ip.getVlanGateway();
                final String vlanNetmask = ip.getVlanNetmask();

                if (ip.isAdd()) {
                    // Add a new guest VLAN and its subnet and bind it to private interface
                    addGuestVlanAndSubnet(guestVlanTag, vlanSelfIp, vlanNetmask, true);
                } else {
                    // Check and delete guest VLAN with this tag, self IP, and netmask
                    deleteGuestVlan(guestVlanTag, vlanSelfIp, vlanNetmask);
                }

                saveConfiguration();
                results[i++] = ip.getPublicIp() + " - success";
                final String action = ip.isAdd() ? "associate" : "remove";
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Netscaler load balancer " + _ip + " successfully executed IPAssocCommand to " + action + " IP " + ip);
                }
            }
        } catch (final ExecutionException e) {
            s_logger.error("Netscaler loadbalancer " + _ip + " failed to execute IPAssocCommand due to " + e.getMessage());
            if (shouldRetry(numRetries)) {
                return retry(cmd, numRetries);
            } else {
                results[i++] = IpAssocAnswer.errorResult;
            }
        }

        return new IpAssocAnswer(cmd, results);
    }

};