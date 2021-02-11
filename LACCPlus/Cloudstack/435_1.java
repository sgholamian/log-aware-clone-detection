//,temp,F5BigIpResource.java,289,326,temp,NetscalerResource.java,445,484
//,3
public class xxx {
    private synchronized Answer execute(IpAssocCommand cmd, int numRetries) {
        String[] results = new String[cmd.getIpAddresses().length];
        int i = 0;
        try {
            IpAddressTO[] ips = cmd.getIpAddresses();
            for (IpAddressTO ip : ips) {
                // is it saver to use Long.valueOf(BroadcastDomain.getValue(ip.getBroadcastUri())) ???
                // i.o.w. can this contain vlan:// then change !!!
                long guestVlanTag = Long.parseLong(ip.getBroadcastUri());
                // It's a hack, using isOneToOneNat field for indicate if it's inline or not
                boolean inline = ip.isOneToOneNat();
                String vlanSelfIp = inline ? tagAddressWithRouteDomain(ip.getVlanGateway(), guestVlanTag) : ip.getVlanGateway();
                String vlanNetmask = ip.getVlanNetmask();

                // Delete any existing guest VLAN with this tag, self IP, and netmask
                deleteGuestVlan(guestVlanTag, vlanSelfIp, vlanNetmask, inline);

                if (ip.isAdd()) {
                    // Add a new guest VLAN
                    addGuestVlan(guestVlanTag, vlanSelfIp, vlanNetmask, inline);
                }

                saveConfiguration();
                results[i++] = ip.getPublicIp() + " - success";
            }

        } catch (ExecutionException e) {
            s_logger.error("Failed to execute IPAssocCommand due to " + e);

            if (shouldRetry(numRetries)) {
                return retry(cmd, numRetries);
            } else {
                results[i++] = IpAssocAnswer.errorResult;
            }
        }

        return new IpAssocAnswer(cmd, results);
    }

};