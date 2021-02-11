//,temp,OvsTunnelManagerImpl.java,189,205,temp,OvsTunnelManagerImpl.java,152,172
//,3
public class xxx {
    @DB
    protected OvsTunnelNetworkVO createTunnelRecord(long from, long to, long networkId, int key) {
        OvsTunnelNetworkVO ta = null;
        try {
            ta = new OvsTunnelNetworkVO(from, to, key, networkId);
            OvsTunnelNetworkVO lock = _tunnelNetworkDao.acquireInLockTable(Long.valueOf(1));
            if (lock == null) {
                s_logger.warn("Cannot lock table ovs_tunnel_account");
                return null;
            }
            _tunnelNetworkDao.persist(ta);
            _tunnelNetworkDao.releaseFromLockTable(lock.getId());
        } catch (EntityExistsException e) {
            s_logger.debug("A record for the tunnel from " + from + " to " + to + " already exists");
        }
        return ta;
    }

};