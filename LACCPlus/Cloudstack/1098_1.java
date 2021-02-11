//,temp,PrivateNetworkGuru.java,139,150,temp,StorageNetworkGuru.java,149,160
//,3
public class xxx {
    @Override
    public void deallocate(Network network, NicProfile nic, VirtualMachineProfile vm) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Deallocate network: networkId: " + nic.getNetworkId() + ", ip: " + nic.getIPv4Address());
        }

        PrivateIpVO ip = _privateIpDao.findByIpAndSourceNetworkId(nic.getNetworkId(), nic.getIPv4Address());
        if (ip != null) {
            _privateIpDao.releaseIpAddress(nic.getIPv4Address(), nic.getNetworkId());
        }
        nic.deallocate();
    }

};