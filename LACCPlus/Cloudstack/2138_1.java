//,temp,JuniperSrxResource.java,664,731,temp,PaloAltoResource.java,436,504
//,3
public class xxx {
    private Answer execute(IpAssocCommand cmd, int numRetries) {
        String[] results = new String[cmd.getIpAddresses().length];
        int i = 0;
        try {
            IpAddressTO ip;
            if (cmd.getIpAddresses().length != 1) {
                throw new ExecutionException("Received an invalid number of guest IPs to associate.");
            } else {
                ip = cmd.getIpAddresses()[0];
            }

            String sourceNatIpAddress = null;
            GuestNetworkType type = GuestNetworkType.INTERFACE_NAT;

            if (ip.isSourceNat()) {
                type = GuestNetworkType.SOURCE_NAT;

                if (ip.getPublicIp() == null) {
                    throw new ExecutionException("Source NAT IP address must not be null.");
                } else {
                    sourceNatIpAddress = ip.getPublicIp();
                }
            }

            long guestVlanTag = Long.parseLong(cmd.getAccessDetail(NetworkElementCommand.GUEST_VLAN_TAG));
            String guestVlanGateway = cmd.getAccessDetail(NetworkElementCommand.GUEST_NETWORK_GATEWAY);
            String cidr = cmd.getAccessDetail(NetworkElementCommand.GUEST_NETWORK_CIDR);
            long cidrSize = NetUtils.cidrToLong(cidr)[1];
            String guestVlanSubnet = NetUtils.getCidrSubNet(guestVlanGateway, cidrSize);

            Long publicVlanTag = null;
            if (ip.getBroadcastUri() != null && !ip.getBroadcastUri().equals("untagged")) {
                try {
                    publicVlanTag = Long.parseLong(BroadcastDomainType.getValue(ip.getBroadcastUri()));
                } catch (Exception e) {
                    throw new ExecutionException("Could not parse public VLAN tag: " + ip.getBroadcastUri());
                }
            }

            openConfiguration();

            // Remove the guest network:
            // Remove source, static, and destination NAT rules
            // Remove VPN
            shutdownGuestNetwork(type, ip.getAccountId(), publicVlanTag, sourceNatIpAddress, guestVlanTag, guestVlanGateway, guestVlanSubnet, cidrSize);

            if (ip.isAdd()) {
                // Implement the guest network for this VLAN
                implementGuestNetwork(type, publicVlanTag, sourceNatIpAddress, guestVlanTag, guestVlanGateway, guestVlanSubnet, cidrSize);
            }

            commitConfiguration();
            results[i++] = ip.getPublicIp() + " - success";
        } catch (ExecutionException e) {
            s_logger.error(e);
            closeConfiguration();

            if (numRetries > 0 && refreshSrxConnection()) {
                int numRetriesRemaining = numRetries - 1;
                s_logger.debug("Retrying IPAssocCommand. Number of retries remaining: " + numRetriesRemaining);
                return execute(cmd, numRetriesRemaining);
            } else {
                results[i++] = IpAssocAnswer.errorResult;
            }
        }

        return new IpAssocAnswer(cmd, results);
    }

};