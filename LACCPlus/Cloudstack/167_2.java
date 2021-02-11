//,temp,GloboDnsElement.java,159,178,temp,GloboDnsElement.java,129,157
//,3
public class xxx {
    @Override
    @DB
    public boolean prepare(final Network network, final NicProfile nic, final VirtualMachineProfile vm, DeployDestination dest, ReservationContext context)
            throws ConcurrentOperationException, ResourceUnavailableException, InsufficientCapacityException {

        if (!isTypeSupported(vm.getType())) {
            s_logger.info("GloboDNS only manages records for VMs of type User, ConsoleProxy and DomainRouter. VM " + vm + " is " + vm.getType());
            return false;
        }

        Long zoneId = network.getDataCenterId();
        final DataCenter zone = _dcDao.findById(zoneId);
        if (zone == null) {
            throw new CloudRuntimeException("Could not find zone associated to this network");
        }

        /* Create new A record in GloboDNS */
        // We allow only lower case names in DNS, so force lower case names for VMs
        String vmName = vm.getHostName();
        String vmHostname = hostNameOfVirtualMachine(vm);
        if (!vmName.equals(vmHostname) && vm.getType() == VirtualMachine.Type.User) {
            throw new InvalidParameterValueException("VM name should contain only lower case letters and digits: " + vmName + " - " + vm);
        }

        CreateOrUpdateRecordAndReverseCommand cmd = new CreateOrUpdateRecordAndReverseCommand(vmHostname, nic.getIPv4Address(), network.getNetworkDomain(),
                GloboDNSTemplateId.value(), GloboDNSOverride.value());
        callCommand(cmd, zoneId);
        return true;
    }

};