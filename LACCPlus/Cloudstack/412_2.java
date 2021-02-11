//,temp,CommandSetupHelper.java,613,629,temp,CommandSetupHelper.java,592,611
//,3
public class xxx {
    public void createVmDataCommandForVMs(final DomainRouterVO router, final Commands cmds, final long guestNetworkId) {
        final List<UserVmVO> vms = _userVmDao.listByNetworkIdAndStates(guestNetworkId, VirtualMachine.State.Running, VirtualMachine.State.Migrating, VirtualMachine.State.Stopping);
        final DataCenterVO dc = _dcDao.findById(router.getDataCenterId());
        for (final UserVmVO vm : vms) {
            boolean createVmData = true;
            if (dc.getNetworkType() == NetworkType.Basic && router.getPodIdToDeployIn().longValue() != vm.getPodIdToDeployIn().longValue()) {
                createVmData = false;
            }

            if (createVmData) {
                final NicVO nic = _nicDao.findByNtwkIdAndInstanceId(guestNetworkId, vm.getId());
                if (nic != null) {
                    s_logger.debug("Creating user data entry for vm " + vm + " on domR " + router);

                    _userVmDao.loadDetails(vm);
                    createVmDataCommand(router, vm, nic, vm.getDetail("SSH.PublicKey"), cmds);
                }
            }
        }
    }

};