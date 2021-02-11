//,temp,StorageNetworkGuru.java,117,147,temp,PodBasedNetworkGuru.java,118,145
//,3
public class xxx {
    @Override
    public void reserve(NicProfile nic, Network network, VirtualMachineProfile vm, DeployDestination dest, ReservationContext context)
        throws InsufficientVirtualNetworkCapacityException, InsufficientAddressCapacityException {
        if (!_sNwMgr.isStorageIpRangeAvailable(dest.getDataCenter().getId())) {
            super.reserve(nic, network, vm, dest, context);
            return;
        }

        Pod pod = dest.getPod();
        Integer vlan = null;

        StorageNetworkIpAddressVO ip = _sNwMgr.acquireIpAddress(pod.getId());
        if (ip == null) {
            throw new InsufficientAddressCapacityException("Unable to get a storage network ip address", Pod.class, pod.getId());
        }

        vlan = ip.getVlan();
        nic.setIPv4Address(ip.getIpAddress());
        nic.setMacAddress(NetUtils.long2Mac(NetUtils.createSequenceBasedMacAddress(ip.getMac(), NetworkModel.MACIdentifier.value())));
        nic.setFormat(AddressFormat.Ip4);
        nic.setIPv4Netmask(ip.getNetmask());
        nic.setBroadcastType(BroadcastDomainType.Storage);
        nic.setIPv4Gateway(ip.getGateway());
        if (vlan != null) {
            nic.setBroadcastUri(BroadcastDomainType.Storage.toUri(vlan));
        } else {
            nic.setBroadcastUri(null);
        }
        nic.setIsolationUri(null);
        s_logger.debug("Allocated a storage nic " + nic + " for " + vm);
    }

};