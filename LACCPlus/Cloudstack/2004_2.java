//,temp,StorageNetworkGuru.java,117,147,temp,PodBasedNetworkGuru.java,118,145
//,3
public class xxx {
    @Override
    public void reserve(NicProfile nic, Network config, VirtualMachineProfile vm, DeployDestination dest, ReservationContext context)
        throws InsufficientVirtualNetworkCapacityException, InsufficientAddressCapacityException {
        Pod pod = dest.getPod();

        boolean forSystemVms = vm.getType().equals(VirtualMachine.Type.ConsoleProxy) || vm.getType().equals(VirtualMachine.Type.SecondaryStorageVm);
        PrivateAllocationData result = _dcDao.allocatePrivateIpAddress(dest.getDataCenter().getId(), dest.getPod().getId(), nic.getId(), context.getReservationId(), forSystemVms);
        if (result == null) {
            throw new InsufficientAddressCapacityException("Unable to get a management ip address", Pod.class, pod.getId());
        }
        Integer vlan = result.getVlan();

        nic.setIPv4Address(result.getIpAddress());
        nic.setMacAddress(NetUtils.long2Mac(NetUtils.createSequenceBasedMacAddress(result.getMacAddress(), NetworkModel.MACIdentifier.value())));
        nic.setIPv4Gateway(pod.getGateway());
        nic.setFormat(AddressFormat.Ip4);
        String netmask = NetUtils.getCidrNetmask(pod.getCidrSize());
        nic.setIPv4Netmask(netmask);
        nic.setBroadcastType(BroadcastDomainType.Native);
        if (vlan != null) {
            nic.setBroadcastUri(BroadcastDomainType.Native.toUri(vlan));
        } else {
            nic.setBroadcastUri(null);
        }
        nic.setIsolationUri(null);

        s_logger.debug("Allocated a nic " + nic + " for " + vm);
    }

};