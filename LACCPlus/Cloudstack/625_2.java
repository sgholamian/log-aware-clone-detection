//,temp,CitrixResourceBase.java,4232,4306,temp,CitrixResourceBase.java,606,676
//,3
public class xxx {
    protected ExecutionResult cleanupNetworkElementCommand(final IpAssocCommand cmd) {
        final Connection conn = getConnection();
        final String routerName = cmd.getAccessDetail(NetworkElementCommand.ROUTER_NAME);
        final String routerIp = cmd.getAccessDetail(NetworkElementCommand.ROUTER_IP);
        final String lastIp = cmd.getAccessDetail(NetworkElementCommand.NETWORK_PUB_LAST_IP);

        try {
            final IpAddressTO[] ips = cmd.getIpAddresses();
            for (final IpAddressTO ip : ips) {

                final VM router = getVM(conn, routerName);

                final NicTO nic = new NicTO();
                nic.setMac(ip.getVifMacAddress());
                nic.setType(ip.getTrafficType());
                if (ip.getBroadcastUri() == null) {
                    nic.setBroadcastType(BroadcastDomainType.Native);
                } else {
                    final URI uri = BroadcastDomainType.fromString(ip.getBroadcastUri());
                    nic.setBroadcastType(BroadcastDomainType.getSchemeValue(uri));
                    nic.setBroadcastUri(uri);
                }
                nic.setDeviceId(0);
                nic.setNetworkRateMbps(ip.getNetworkRate());
                nic.setName(ip.getNetworkName());

                Network network = getNetwork(conn, nic);

                // If we are disassociating the last IP address in the VLAN, we
                // need
                // to remove a VIF
                boolean removeVif = false;

                // there is only one ip in this public vlan and removing it, so
                // remove the nic
                if (org.apache.commons.lang.StringUtils.equalsIgnoreCase(lastIp, "true") && !ip.isAdd()) {
                    final VIF correctVif = getCorrectVif(conn, router, network);
                    // in isolated network eth2 is the default public interface. We don't want to delete it.
                    if (correctVif != null && !correctVif.getDevice(conn).equals("2")) {
                        removeVif = true;
                    }
                }

                if (removeVif) {

                    // Determine the correct VIF on DomR to
                    // associate/disassociate the
                    // IP address with

                    final VIF correctVif = getCorrectVif(conn, router, network);
                    if (correctVif != null) {
                        network = correctVif.getNetwork(conn);

                        // Mark this vif to be removed from network usage
                        networkUsage(conn, routerIp, "deleteVif", "eth" + correctVif.getDevice(conn));

                        // Remove the VIF from DomR
                        correctVif.unplug(conn);
                        correctVif.destroy(conn);

                        // Disable the VLAN network if necessary
                        disableVlanNetwork(conn, network);
                    }
                }
            }
        } catch (final Exception e) {
            s_logger.debug("Ip Assoc failure on applying one ip due to exception:  ", e);
            return new ExecutionResult(false, e.getMessage());
        }
        return new ExecutionResult(true, null);
    }

};