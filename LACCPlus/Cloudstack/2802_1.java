//,temp,ContrailElementImpl.java,145,206,temp,ContrailGuru.java,215,291
//,3
public class xxx {
    @Override
    public boolean prepare(Network network, NicProfile nicProfile, VirtualMachineProfile vm, DeployDestination dest, ReservationContext context)
        throws ConcurrentOperationException, ResourceUnavailableException, InsufficientCapacityException {

        s_logger.debug("NetworkElement prepare: " + network.getName() + ", traffic type: " + network.getTrafficType());

        if (network.getTrafficType() == TrafficType.Guest) {
            s_logger.debug("ignore network " + network.getName());
            return true;
        }

        s_logger.debug("network: " + network.getId());

        VirtualNetworkModel vnModel = _manager.getDatabase().lookupVirtualNetwork(network.getUuid(), _manager.getCanonicalName(network), network.getTrafficType());

        if (vnModel == null) {
            // There is no notification after a physical network is associated with the VRouter NetworkOffering
            // this may be the first time we see this network.
            return false;
        }

        VirtualMachineModel vmModel = _manager.getDatabase().lookupVirtualMachine(vm.getUuid());
        if (vmModel == null) {
            VMInstanceVO vmVo = (VMInstanceVO)vm.getVirtualMachine();
            vmModel = new VirtualMachineModel(vmVo, vm.getUuid());
            vmModel.setProperties(_manager.getModelController(), vmVo);
        }

        NicVO nic = _nicDao.findById(nicProfile.getId());
        assert nic != null;

        VMInterfaceModel vmiModel = vmModel.getVMInterface(nic.getUuid());
        if (vmiModel == null) {
            vmiModel = new VMInterfaceModel(nic.getUuid());
            vmiModel.addToVirtualMachine(vmModel);
            vmiModel.addToVirtualNetwork(vnModel);
        }

        try {
            vmiModel.build(_manager.getModelController(), (VMInstanceVO)vm.getVirtualMachine(), nic);
        } catch (IOException ex) {
            s_logger.warn("vm interface set", ex);
            return false;
        }

        InstanceIpModel ipModel = vmiModel.getInstanceIp();
        if (ipModel == null) {
            ipModel = new InstanceIpModel(vm.getInstanceName(), nic.getDeviceId());
            ipModel.addToVMInterface(vmiModel);
        }
        ipModel.setAddress(nicProfile.getIPv4Address());

        try {
            vmModel.update(_manager.getModelController());
        } catch (Exception ex) {
            s_logger.warn("virtual-machine-update", ex);
            return false;
        }
        _manager.getDatabase().getVirtualMachines().add(vmModel);

        return true;
    }

};