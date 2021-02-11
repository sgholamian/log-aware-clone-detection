//,temp,CommandSetupHelper.java,613,629,temp,CommandSetupHelper.java,592,611
//,3
public class xxx {
    public void createDhcpEntryCommandsForVMs(final DomainRouterVO router, final Commands cmds, final long guestNetworkId) {
        final List<UserVmVO> vms = _userVmDao.listByNetworkIdAndStates(guestNetworkId, VirtualMachine.State.Running, VirtualMachine.State.Migrating, VirtualMachine.State.Stopping);
        final DataCenterVO dc = _dcDao.findById(router.getDataCenterId());
        String dnsBasicZoneUpdates = _configDao.getValue(Config.DnsBasicZoneUpdates.key());
        for (final UserVmVO vm : vms) {
            if (dc.getNetworkType() == NetworkType.Basic && router.getPodIdToDeployIn().longValue() != vm.getPodIdToDeployIn().longValue()
                    && dnsBasicZoneUpdates.equalsIgnoreCase("pod")) {
                continue;
            }

            final NicVO nic = _nicDao.findByNtwkIdAndInstanceId(guestNetworkId, vm.getId());
            if (nic != null) {
                s_logger.debug("Creating dhcp entry for vm " + vm + " on domR " + router + ".");
                createDhcpEntryCommand(router, vm, nic, false, cmds);
            }
        }
    }

};