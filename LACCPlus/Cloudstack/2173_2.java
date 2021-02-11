//,temp,IvsVifDriver.java,79,140,temp,OvsVifDriver.java,90,178
//,3
public class xxx {
    @Override
    public InterfaceDef plug(NicTO nic, String guestOsType, String nicAdapter, Map<String, String> extraConfig) throws InternalErrorException, LibvirtException {
        s_logger.debug("plugging nic=" + nic);

        LibvirtVMDef.InterfaceDef intf = new LibvirtVMDef.InterfaceDef();
        if (!_libvirtComputingResource.dpdkSupport || nic.isDpdkDisabled()) {
            // Let libvirt handle OVS ports creation when DPDK property is disabled or when it is enabled but disabled for the nic
            // For DPDK support, libvirt does not handle ports creation, invoke 'addDpdkPort' method
            intf.setVirtualPortType("openvswitch");
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
        if (nic.getType() == Networks.TrafficType.Guest) {
            Integer networkRateKBps = (nic.getNetworkRateMbps() != null && nic.getNetworkRateMbps().intValue() != -1) ? nic.getNetworkRateMbps().intValue() * 128 : 0;
            if ((nic.getBroadcastType() == Networks.BroadcastDomainType.Vlan || nic.getBroadcastType() == Networks.BroadcastDomainType.Pvlan) &&
                    !vlanId.equalsIgnoreCase("untagged")) {
                if (trafficLabel != null && !trafficLabel.isEmpty()) {
                    if (_libvirtComputingResource.dpdkSupport && !nic.isDpdkDisabled()) {
                        s_logger.debug("DPDK support enabled: configuring per traffic label " + trafficLabel);
                        String dpdkOvsPath = _libvirtComputingResource.dpdkOvsPath;
                        if (StringUtils.isBlank(dpdkOvsPath)) {
                            throw new CloudRuntimeException("DPDK is enabled on the host but no OVS path has been provided");
                        }
                        String port = dpdkDriver.getNextDpdkPort();
                        DPDKHelper.VHostUserMode dpdKvHostUserMode = dpdkDriver.getDPDKvHostUserMode(extraConfig);
                        dpdkDriver.addDpdkPort(_pifs.get(trafficLabel), port, vlanId, dpdKvHostUserMode, dpdkOvsPath);
                        String interfaceMode = dpdkDriver.getGuestInterfacesModeFromDPDKVhostUserMode(dpdKvHostUserMode);
                        intf.defDpdkNet(dpdkOvsPath, port, nic.getMac(),
                                getGuestNicModel(guestOsType, nicAdapter), 0,
                                dpdkDriver.getExtraDpdkProperties(extraConfig),
                                interfaceMode);
                    } else {
                        s_logger.debug("creating a vlan dev and bridge for guest traffic per traffic label " + trafficLabel);
                        intf.defBridgeNet(_pifs.get(trafficLabel), null, nic.getMac(), getGuestNicModel(guestOsType, nicAdapter), networkRateKBps);
                        intf.setVlanTag(Integer.parseInt(vlanId));
                    }
                } else {
                    intf.defBridgeNet(_pifs.get("private"), null, nic.getMac(), getGuestNicModel(guestOsType, nicAdapter), networkRateKBps);
                    intf.setVlanTag(Integer.parseInt(vlanId));
                }
            } else if (nic.getBroadcastType() == Networks.BroadcastDomainType.Lswitch || nic.getBroadcastType() == Networks.BroadcastDomainType.OpenDaylight) {
                s_logger.debug("nic " + nic + " needs to be connected to LogicalSwitch " + logicalSwitchUuid);
                intf.setVirtualPortInterfaceId(nic.getUuid());
                String brName = (trafficLabel != null && !trafficLabel.isEmpty()) ? _pifs.get(trafficLabel) : _pifs.get("private");
                intf.defBridgeNet(brName, null, nic.getMac(), getGuestNicModel(guestOsType, nicAdapter), networkRateKBps);
            } else if (nic.getBroadcastType() == Networks.BroadcastDomainType.Vswitch) {
                String vnetId = Networks.BroadcastDomainType.getValue(nic.getBroadcastUri());
                String brName = "OVSTunnel" + vnetId;
                s_logger.debug("nic " + nic + " needs to be connected to LogicalSwitch " + brName);
                intf.defBridgeNet(brName, null, nic.getMac(), getGuestNicModel(guestOsType, nicAdapter), networkRateKBps);
            } else {
                intf.defBridgeNet(_bridges.get("guest"), null, nic.getMac(), getGuestNicModel(guestOsType, nicAdapter), networkRateKBps);
            }
        } else if (nic.getType() == Networks.TrafficType.Control) {
            /* Make sure the network is still there */
            createControlNetwork(_bridges.get("linklocal"));
            intf.defBridgeNet(_bridges.get("linklocal"), null, nic.getMac(), getGuestNicModel(guestOsType, nicAdapter));
        } else if (nic.getType() == Networks.TrafficType.Public) {
            Integer networkRateKBps = (nic.getNetworkRateMbps() != null && nic.getNetworkRateMbps().intValue() != -1) ? nic.getNetworkRateMbps().intValue() * 128 : 0;
            if (nic.getBroadcastType() == Networks.BroadcastDomainType.Vlan && !vlanId.equalsIgnoreCase("untagged")) {
                if (trafficLabel != null && !trafficLabel.isEmpty()) {
                    s_logger.debug("creating a vlan dev and bridge for public traffic per traffic label " + trafficLabel);
                    intf.defBridgeNet(_pifs.get(trafficLabel), null, nic.getMac(), getGuestNicModel(guestOsType, nicAdapter), networkRateKBps);
                    intf.setVlanTag(Integer.parseInt(vlanId));
                } else {
                    intf.defBridgeNet(_pifs.get("public"), null, nic.getMac(), getGuestNicModel(guestOsType, nicAdapter), networkRateKBps);
                    intf.setVlanTag(Integer.parseInt(vlanId));
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
        return intf;
    }

};