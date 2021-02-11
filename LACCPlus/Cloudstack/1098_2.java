//,temp,PrivateNetworkGuru.java,139,150,temp,StorageNetworkGuru.java,149,160
//,3
public class xxx {
    @Override
    public boolean release(NicProfile nic, VirtualMachineProfile vm, String reservationId) {
        Network nw = _nwDao.findById(nic.getNetworkId());
        if (!_sNwMgr.isStorageIpRangeAvailable(nw.getDataCenterId())) {
            return super.release(nic, vm, reservationId);
        }

        _sNwMgr.releaseIpAddress(nic.getIPv4Address());
        s_logger.debug("Release an storage ip " + nic.getIPv4Address());
        nic.deallocate();
        return true;
    }

};