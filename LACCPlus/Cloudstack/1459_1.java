//,temp,BridgeVifDriver.java,191,264,temp,IvsVifDriver.java,79,140
//,3
public class xxx {
    @Override
    public LibvirtVMDef.InterfaceDef plug(NicTO nic, String guestOsType, String nicAdapter, Map<String, String> extraConfig) throws InternalErrorException, LibvirtException {

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("nic=" + nic);
            if (nicAdapter != null && !nicAdapter.isEmpty()) {
                s_logger.debug("custom nic adapter=" + nicAdapter);
            }
        }

        LibvirtVMDef.InterfaceDef intf = new LibvirtVMDef.InterfaceDef();

        String vNetId = null;
        String protocol = null;
        if (isBroadcastTypeVlanOrVxlan(nic)) {
            vNetId = Networks.BroadcastDomainType.getValue(nic.getBroadcastUri());
            protocol = Networks.BroadcastDomainType.getSchemeValue(nic.getBroadcastUri()).scheme();
        } else if (nic.getBroadcastType() == Networks.BroadcastDomainType.Lswitch) {
            throw new InternalErrorException("Nicira NVP Logicalswitches are not supported by the BridgeVifDriver");
        }
        String trafficLabel = nic.getName();
        Integer networkRateKBps = 0;
        if (libvirtVersion > ((10 * 1000 + 10))) {
            networkRateKBps = (nic.getNetworkRateMbps() != null && nic.getNetworkRateMbps().intValue() != -1) ? nic.getNetworkRateMbps().intValue() * 128 : 0;
        }

        if (nic.getType() == Networks.TrafficType.Guest) {
            if (isBroadcastTypeVlanOrVxlan(nic) && isValidProtocolAndVnetId(vNetId, protocol)) {
                    if (trafficLabel != null && !trafficLabel.isEmpty()) {
                        s_logger.debug("creating a vNet dev and bridge for guest traffic per traffic label " + trafficLabel);
                        String brName = createVnetBr(vNetId, trafficLabel, protocol);
                        intf.defBridgeNet(brName, null, nic.getMac(), getGuestNicModel(guestOsType, nicAdapter), networkRateKBps);
                    } else {
                        String brName = createVnetBr(vNetId, "private", protocol);
                        intf.defBridgeNet(brName, null, nic.getMac(), getGuestNicModel(guestOsType, nicAdapter), networkRateKBps);
                    }
            } else {
                String brname = "";
                if (trafficLabel != null && !trafficLabel.isEmpty()) {
                    brname = trafficLabel;
                } else {
                    brname = _bridges.get("guest");
                }
                intf.defBridgeNet(brname, null, nic.getMac(), getGuestNicModel(guestOsType, nicAdapter), networkRateKBps);
            }
        } else if (nic.getType() == Networks.TrafficType.Control) {
            /* Make sure the network is still there */
            createControlNetwork();
            intf.defBridgeNet(_bridges.get("linklocal"), null, nic.getMac(), getGuestNicModel(guestOsType, nicAdapter));
        } else if (nic.getType() == Networks.TrafficType.Public) {
            if (isBroadcastTypeVlanOrVxlan(nic) && isValidProtocolAndVnetId(vNetId, protocol)) {
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
        if (nic.getPxeDisable()) {
            intf.setPxeDisable(true);
        }

        return intf;
    }

};