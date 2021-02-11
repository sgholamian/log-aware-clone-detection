//,temp,NiciraNvpElement.java,803,842,temp,NiciraNvpElement.java,453,491
//,3
public class xxx {
    @Override
    public boolean shutdown(Network network, ReservationContext context, boolean cleanup) throws ConcurrentOperationException, ResourceUnavailableException {
        if (!canHandle(network, Service.Connectivity)) {
            return false;
        }

        List<NiciraNvpDeviceVO> devices = niciraNvpDao.listByPhysicalNetwork(network.getPhysicalNetworkId());
        if (devices.isEmpty()) {
            s_logger.error("No NiciraNvp Controller on physical network " + network.getPhysicalNetworkId());
            return false;
        }
        NiciraNvpDeviceVO niciraNvpDevice = devices.get(0);
        HostVO niciraNvpHost = hostDao.findById(niciraNvpDevice.getHostId());

        //Dont destroy logical router when removing Shared Networks
        if (! network.getGuestType().equals(GuestType.Shared) && networkModel.isProviderSupportServiceInNetwork(network.getId(), Service.SourceNat, Provider.NiciraNvp)) {
            s_logger.debug("Apparently we were providing SourceNat on this network");

            // Deleting the LogicalRouter will also take care of all provisioned
            // nat rules.
            NiciraNvpRouterMappingVO routermapping = niciraNvpRouterMappingDao.findByNetworkId(network.getId());
            if (routermapping == null) {
                s_logger.warn("No logical router uuid found for network " + network.getDisplayText());
                // This might be cause by a failed deployment, so don't make shutdown fail as well.
                return true;
            }

            DeleteLogicalRouterCommand cmd = new DeleteLogicalRouterCommand(routermapping.getLogicalRouterUuid());
            DeleteLogicalRouterAnswer answer = (DeleteLogicalRouterAnswer)agentMgr.easySend(niciraNvpHost.getId(), cmd);
            if (answer.getResult() == false) {
                s_logger.error("Failed to delete LogicalRouter for network " + network.getDisplayText());
                return false;
            }

            niciraNvpRouterMappingDao.remove(routermapping.getId());
        }

        return true;
    }

};