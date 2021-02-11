//,temp,GloboDnsElement.java,159,178,temp,GloboDnsElement.java,129,157
//,3
public class xxx {
    @Override
    @DB
    public boolean release(final Network network, NicProfile nic, final VirtualMachineProfile vm, ReservationContext context) throws ConcurrentOperationException,
            ResourceUnavailableException {

        if (!isTypeSupported(vm.getType())) {
            s_logger.info("GloboDNS only manages records for VMs of type User, ConsoleProxy and DomainRouter. VM " + vm + " is " + vm.getType());
            return false;
        }

        Long zoneId = network.getDataCenterId();
        final DataCenter zone = _dcDao.findById(zoneId);
        if (zone == null) {
            throw new CloudRuntimeException("Could not find zone associated to this network");
        }

        RemoveRecordCommand cmd = new RemoveRecordCommand(hostNameOfVirtualMachine(vm), nic.getIPv4Address(), network.getNetworkDomain(), GloboDNSOverride.value());
        callCommand(cmd, zoneId);
        return true;
    }

};