//,temp,BridgeVifDriver.java,191,264,temp,IvsVifDriver.java,79,140
//,3
public class xxx {
    @Override
    public InterfaceDef plug(NicTO nic, String guestOsType, String nicAdapter, Map<String, String> extraConfig) throws InternalErrorException, LibvirtException {
        LibvirtVMDef.InterfaceDef intf = new LibvirtVMDef.InterfaceDef();

        String vNetId = null;
        String protocol = null;
        if (nic.getBroadcastType() == Networks.BroadcastDomainType.Vlan || nic.getBroadcastType() == Networks.BroadcastDomainType.Vxlan) {
            vNetId = Networks.BroadcastDomainType.getValue(nic.getBroadcastUri());
            protocol = Networks.BroadcastDomainType.getSchemeValue(nic.getBroadcastUri()).scheme();
        }

        String vlanId = null;
        String logicalSwitchUuid = null;
        if (nic.getBroadcastType() == Networks.BroadcastDomainType.Vlan) {
            vlanId = Networks.BroadcastDomainType.getValue(nic.getBroadcastUri());
        } else if (nic.getBroadcastType() == Networks.BroadcastDomainType.Lswitch) {
            logicalSwitchUuid = Networks.BroadcastDomainType.getValue(nic.getBroadcastUri());
        } else if (nic.getBroadcastType() == Networks.BroadcastDomainType.Pvlan) {
            // TODO consider moving some of this functionality from NetUtils to Networks....
            vlanId = NetUtils.getPrimaryPvlanFromUri(nic.getBroadcastUri());
        }
        String trafficLabel = nic.getName();
        Integer networkRateKBps = (nic.getNetworkRateMbps() != null && nic.getNetworkRateMbps().intValue() != -1) ? nic.getNetworkRateMbps().intValue() * 128 : 0;
        if (nic.getType() == Networks.TrafficType.Guest) {
            if ((nic.getBroadcastType() == Networks.BroadcastDomainType.Vlan || nic.getBroadcastType() == Networks.BroadcastDomainType.Pvlan) &&
                    !vlanId.equalsIgnoreCase("untagged")) {
                if (trafficLabel != null && !trafficLabel.isEmpty()) {
                    s_logger.debug("creating a vlan dev and bridge for guest traffic per traffic label " + trafficLabel);
                    intf.defEthernet("ivsnet-" + nic.getUuid().substring(0, 5), nic.getMac(), getGuestNicModel(guestOsType, nicAdapter), _ivsIfUpPath, networkRateKBps);
                } else {
                    throw new InternalErrorException("no traffic label ");
                }
            }
        } else if (nic.getType() == Networks.TrafficType.Control) {
            /* Make sure the network is still there */
            createControlNetwork();
            intf.defBridgeNet(_bridges.get("linklocal"), null, nic.getMac(), getGuestNicModel(guestOsType, nicAdapter));
        } else if (nic.getType() == Networks.TrafficType.Public) {
            if ((nic.getBroadcastType() == Networks.BroadcastDomainType.Vlan) && (vNetId != null) && (protocol != null) && (!vNetId.equalsIgnoreCase("untagged")) ||
                    (nic.getBroadcastType() == Networks.BroadcastDomainType.Vxlan)) {
                if (trafficLabel != null && !trafficLabel.isEmpty()) {
                    s_logger.debug("creating a vNet dev and bridge for public traffic per traffic label " + trafficLabel);
                    String brName = createVnetBr(vNetId, trafficLabel, protocol);
                    intf.defBridgeNet(brName, null, nic.getMac(), getGuestNicModel(guestOsType, nicAdapter), networkRateKBps);
                } else {
                    String brName = createVnetBr(vNetId, "public", protocol);
                    intf.defBridgeNet(brName, null, nic.getMac(), getGuestNicModel(guestOsType, nicAdapter), networkRateKBps);
                }
            } else {
                intf.defBridgeNet(_bridges.get("public"), null, nic.getMac(), getGuestNicModel(guestOsType, nicAdapter), networkRateKBps);
            }
        } else if (nic.getType() == Networks.TrafficType.Management) {
            intf.defBridgeNet(_bridges.get("private"), null, nic.getMac(), getGuestNicModel(guestOsType, nicAdapter));
        } else if (nic.getType() == Networks.TrafficType.Storage) {
            String storageBrName = nic.getName() == null ? _bridges.get("private") : nic.getName();
            intf.defBridgeNet(storageBrName, null, nic.getMac(), getGuestNicModel(guestOsType, nicAdapter));
        }
        if (nic.getPxeDisable() == true) {
            intf.setPxeDisable(true);
        }
        return intf;
    }

};