//,temp,NiciraNvpElement.java,887,920,temp,NiciraNvpElement.java,411,451
//,3
public class xxx {
    @Override
    public boolean release(Network network, NicProfile nic, VirtualMachineProfile vm, ReservationContext context) throws ConcurrentOperationException,
    ResourceUnavailableException {

        if (!canHandle(network, Service.Connectivity)) {
            return false;
        }

        if (network.getBroadcastUri() == null) {
            s_logger.error("Nic has no broadcast Uri with the LSwitch Uuid");
            return false;
        }

        NicVO nicVO = nicDao.findById(nic.getId());

        List<NiciraNvpDeviceVO> devices = niciraNvpDao.listByPhysicalNetwork(network.getPhysicalNetworkId());
        if (devices.isEmpty()) {
            s_logger.error("No NiciraNvp Controller on physical network " + network.getPhysicalNetworkId());
            return false;
        }
        NiciraNvpDeviceVO niciraNvpDevice = devices.get(0);
        HostVO niciraNvpHost = hostDao.findById(niciraNvpDevice.getHostId());

        NiciraNvpNicMappingVO nicMap = niciraNvpNicMappingDao.findByNicUuid(nicVO.getUuid());
        if (nicMap == null) {
            s_logger.error("No mapping for nic " + nic.getName());
            return false;
        }

        DeleteLogicalSwitchPortCommand cmd = new DeleteLogicalSwitchPortCommand(nicMap.getLogicalSwitchUuid(), nicMap.getLogicalSwitchPortUuid());
        DeleteLogicalSwitchPortAnswer answer = (DeleteLogicalSwitchPortAnswer)agentMgr.easySend(niciraNvpHost.getId(), cmd);

        if (answer == null || !answer.getResult()) {
            s_logger.error("DeleteLogicalSwitchPortCommand failed");
            return false;
        }

        niciraNvpNicMappingDao.remove(nicMap.getId());

        return true;
    }

};