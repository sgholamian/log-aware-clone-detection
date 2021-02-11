//,temp,BaremetaNetworkGuru.java,152,172,temp,IpAddressManagerImpl.java,2022,2072
//,3
public class xxx {
    private void getBaremetalIp(NicProfile nic, Pod pod, VirtualMachineProfile vm, Network network, String requiredIp) throws
        InsufficientAddressCapacityException, ConcurrentOperationException {
        DataCenter dc = _dcDao.findById(pod.getDataCenterId());
        if (nic.getIPv4Address() == null) {
            s_logger.debug(String.format("Requiring ip address: %s", nic.getIPv4Address()));
            PublicIp ip = _ipAddrMgr.assignPublicIpAddress(dc.getId(), pod.getId(), vm.getOwner(), VlanType.DirectAttached, network.getId(), requiredIp, false, false);
            nic.setIPv4Address(ip.getAddress().toString());
            nic.setFormat(AddressFormat.Ip4);
            nic.setIPv4Gateway(ip.getGateway());
            nic.setIPv4Netmask(ip.getNetmask());
            if (ip.getVlanTag() != null && ip.getVlanTag().equalsIgnoreCase(Vlan.UNTAGGED)) {
                nic.setIsolationUri(IsolationType.Ec2.toUri(Vlan.UNTAGGED));
                nic.setBroadcastUri(BroadcastDomainType.Vlan.toUri(Vlan.UNTAGGED));
                nic.setBroadcastType(BroadcastDomainType.Native);
            }
            nic.setReservationId(String.valueOf(ip.getVlanTag()));
            nic.setMacAddress(ip.getMacAddress());
        }
        nic.setIPv4Dns1(dc.getDns1());
        nic.setIPv4Dns2(dc.getDns2());
    }

};