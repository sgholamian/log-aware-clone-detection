//,temp,VirtualRouterElement.java,1218,1240,temp,VirtualRouterElement.java,1193,1216
//,3
public class xxx {
    @Override
    public boolean prepareMigration(final NicProfile nic, final Network network, final VirtualMachineProfile vm, final DeployDestination dest, final ReservationContext context) {
        if (nic.getBroadcastType() != Networks.BroadcastDomainType.Pvlan) {
            return true;
        }
        if (vm.getType() == VirtualMachine.Type.DomainRouter) {
            assert vm instanceof DomainRouterVO;
            final DomainRouterVO router = (DomainRouterVO) vm.getVirtualMachine();

            final DataCenterVO dcVO = _dcDao.findById(network.getDataCenterId());
            final NetworkTopology networkTopology = networkTopologyContext.retrieveNetworkTopology(dcVO);

            try {
                networkTopology.setupDhcpForPvlan(false, router, router.getHostId(), nic);
            } catch (final ResourceUnavailableException e) {
                s_logger.warn("Timed Out", e);
            }
        } else if (vm.getType() == VirtualMachine.Type.User) {
            assert vm instanceof UserVmVO;
            final UserVmVO userVm = (UserVmVO) vm.getVirtualMachine();
            _userVmMgr.setupVmForPvlan(false, userVm.getHostId(), nic);
        }
        return true;
    }

};