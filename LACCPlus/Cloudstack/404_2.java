//,temp,BrocadeVcsGuestNetworkGuru.java,189,213,temp,OpendaylightGuestNetworkGuru.java,214,240
//,3
public class xxx {
    @Override
    public boolean release(NicProfile nic, VirtualMachineProfile vm, String reservationId) {
        boolean success = super.release(nic, vm, reservationId);

        if (success) {
            //get physical network id
            NetworkVO network = _networkDao.findById(nic.getNetworkId());
            Long physicalNetworkId = network.getPhysicalNetworkId();

            List<OpenDaylightControllerVO> devices = openDaylightControllerMappingDao.listByPhysicalNetwork(physicalNetworkId);
            if (devices.isEmpty()) {
                s_logger.error("No Controller on physical network " + physicalNetworkId);
                throw new CloudRuntimeException("No OpenDaylight controller on this physical network");
            }
            OpenDaylightControllerVO controller = devices.get(0);

            DestroyPortCommand cmd = new DestroyPortCommand(UUID.fromString(nic.getUuid()));
            DestroyPortAnswer answer = (DestroyPortAnswer)agentManager.easySend(controller.getHostId(), cmd);

            if (answer == null || !answer.getResult()) {
                s_logger.error("DestroyPortCommand failed");
                success = false;
            }
        }

        return success;
    }

};