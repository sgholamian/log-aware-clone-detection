//,temp,OvsTunnelManagerImpl.java,446,465,temp,OvsTunnelManagerImpl.java,423,444
//,3
public class xxx {
    @DB
    private void handleDestroyBridgeAnswer(Answer ans, long hostId, long networkId) {

        if (ans.getResult()) {
            OvsTunnelNetworkVO lock = _tunnelNetworkDao.acquireInLockTable(Long.valueOf(1));
            if (lock == null) {
                s_logger.warn("failed to lock ovs_tunnel_network," + "remove record");
                return;
            }

            _tunnelNetworkDao.removeByFromNetwork(hostId, networkId);
            _tunnelNetworkDao.releaseFromLockTable(lock.getId());

            s_logger.debug(String.format("Destroy bridge for" +
                    "network %1$s successful", networkId));
        } else {
            s_logger.debug(String.format("Destroy bridge for" +
                    "network %1$s failed", networkId));
        }
    }

};