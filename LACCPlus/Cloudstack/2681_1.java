//,temp,LibvirtComputingResource.java,1833,1891,temp,LibvirtComputingResource.java,1784,1831
//,3
public class xxx {
    protected ExecutionResult cleanupNetworkElementCommand(final IpAssocCommand cmd) {

        final String routerName = cmd.getAccessDetail(NetworkElementCommand.ROUTER_NAME);
        final String routerIp = cmd.getAccessDetail(NetworkElementCommand.ROUTER_IP);
        final String lastIp = cmd.getAccessDetail(NetworkElementCommand.NETWORK_PUB_LAST_IP);
        Connect conn;


        try{
            conn = LibvirtConnection.getConnectionByVmName(routerName);
            final List<InterfaceDef> nics = getInterfaces(conn, routerName);
            final Map<String, Integer> broadcastUriAllocatedToVM = new HashMap<String, Integer>();

            Integer nicPos = 0;
            for (final InterfaceDef nic : nics) {
                if (nic.getBrName().equalsIgnoreCase(_linkLocalBridgeName)) {
                    broadcastUriAllocatedToVM.put("LinkLocal", nicPos);
                } else {
                    if (nic.getBrName().equalsIgnoreCase(_publicBridgeName) || nic.getBrName().equalsIgnoreCase(_privBridgeName) ||
                            nic.getBrName().equalsIgnoreCase(_guestBridgeName)) {
                        broadcastUriAllocatedToVM.put(BroadcastDomainType.Vlan.toUri(Vlan.UNTAGGED).toString(), nicPos);
                    } else {
                        final String broadcastUri = getBroadcastUriFromBridge(nic.getBrName());
                        broadcastUriAllocatedToVM.put(broadcastUri, nicPos);
                    }
                }
                nicPos++;
            }

            final IpAddressTO[] ips = cmd.getIpAddresses();
            int nicNum = 0;
            for (final IpAddressTO ip : ips) {

                if (!broadcastUriAllocatedToVM.containsKey(ip.getBroadcastUri())) {
                    /* plug a vif into router */
                    VifHotPlug(conn, routerName, ip.getBroadcastUri(), ip.getVifMacAddress());
                    broadcastUriAllocatedToVM.put(ip.getBroadcastUri(), nicPos++);
                }
                nicNum = broadcastUriAllocatedToVM.get(ip.getBroadcastUri());

                if (org.apache.commons.lang.StringUtils.equalsIgnoreCase(lastIp, "true") && !ip.isAdd()) {
                    // in isolated network eth2 is the default public interface. We don't want to delete it.
                    if (nicNum != 2) {
                        vifHotUnPlug(conn, routerName, ip.getVifMacAddress());
                        networkUsage(routerIp, "deleteVif", "eth" + nicNum);
                    }
                }
            }

        } catch (final LibvirtException e) {
            s_logger.error("ipassoccmd failed", e);
            return new ExecutionResult(false, e.getMessage());
        } catch (final InternalErrorException e) {
            s_logger.error("ipassoccmd failed", e);
            return new ExecutionResult(false, e.getMessage());
        }

        return new ExecutionResult(true, null);
    }

};