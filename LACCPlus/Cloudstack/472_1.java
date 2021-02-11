//,temp,ContrailElementImpl.java,208,246,temp,ContrailGuru.java,307,338
//,3
public class xxx {
    @Override
    public boolean release(Network network, NicProfile nicProfile, VirtualMachineProfile vm, ReservationContext context) throws ConcurrentOperationException,
        ResourceUnavailableException {
        if (network.getTrafficType() == TrafficType.Guest) {
            return true;
        } else if (!_manager.isManagedPhysicalNetwork(network)) {
            s_logger.debug("release ignore network " + network.getId());
            return true;
        }

        NicVO nic = _nicDao.findById(nicProfile.getId());
        assert nic != null;

        VirtualMachineModel vmModel = _manager.getDatabase().lookupVirtualMachine(vm.getUuid());
        if (vmModel == null) {
            s_logger.debug("vm " + vm.getInstanceName() + " not in local database");
            return true;
        }
        VMInterfaceModel vmiModel = vmModel.getVMInterface(nic.getUuid());
        if (vmiModel != null) {
            try {
                vmiModel.destroy(_manager.getModelController());
            } catch (IOException ex) {
                s_logger.warn("virtual-machine-interface delete", ex);
            }
            vmModel.removeSuccessor(vmiModel);
        }

        if (!vmModel.hasDescendents()) {
            _manager.getDatabase().getVirtualMachines().remove(vmModel);
            try {
                vmModel.delete(_manager.getModelController());
            } catch (IOException e) {
                return false;
            }
        }

        return true;
    }

};