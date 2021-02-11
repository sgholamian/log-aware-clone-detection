//,temp,ContrailElementImpl.java,208,246,temp,ContrailGuru.java,307,338
//,3
public class xxx {
    @Override
    public void deallocate(Network network, NicProfile nic, VirtualMachineProfile vm) {
        s_logger.debug("deallocate NicProfile " + nic.getId() + " on " + network.getName());
        NicVO nicVO = _nicDao.findById(nic.getId());
        assert nicVO != null;

        VirtualMachineModel vmModel = _manager.getDatabase().lookupVirtualMachine(vm.getUuid());
        if (vmModel == null) {
            return;
        }
        VMInterfaceModel vmiModel = vmModel.getVMInterface(nicVO.getUuid());
        if (vmiModel == null) {
            return;
        }
        try {
            vmiModel.destroy(_manager.getModelController());
        } catch (IOException ex) {
            return;
        }
        vmModel.removeSuccessor(vmiModel);

        if (!vmModel.hasDescendents()) {
            _manager.getDatabase().getVirtualMachines().remove(vmModel);
            try {
                vmModel.delete(_manager.getModelController());
            } catch (IOException ex) {
                s_logger.warn("virtual-machine delete", ex);
                return;
            }
        }

    }

};