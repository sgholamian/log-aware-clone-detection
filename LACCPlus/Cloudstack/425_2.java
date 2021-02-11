//,temp,VirtualRouterElement.java,828,846,temp,VirtualRouterElement.java,730,751
//,3
public class xxx {
    @Override
    public boolean saveSSHKey(final Network network, final NicProfile nic, final VirtualMachineProfile vm, final String sshPublicKey) throws ResourceUnavailableException {
        if (!canHandle(network, null)) {
            return false;
        }
        final List<DomainRouterVO> routers = _routerDao.listByNetworkAndRole(network.getId(), Role.VIRTUAL_ROUTER);
        if (routers == null || routers.isEmpty()) {
            s_logger.debug("Can't find virtual router element in network " + network.getId());
            return true;
        }

        final VirtualMachineProfile uservm = vm;

        final DataCenterVO dcVO = _dcDao.findById(network.getDataCenterId());
        final NetworkTopology networkTopology = networkTopologyContext.retrieveNetworkTopology(dcVO);

        boolean result = true;
        for (final DomainRouterVO domainRouterVO : routers) {
            result = result && networkTopology.saveSSHPublicKeyToRouter(network, nic, uservm, domainRouterVO, sshPublicKey);
        }
        return result;
    }

};