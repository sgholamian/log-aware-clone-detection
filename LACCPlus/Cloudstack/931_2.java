//,temp,OvsTunnelManagerImpl.java,446,465,temp,OvsTunnelManagerImpl.java,423,444
//,3
public class xxx {
    @DB
    private void handleDestroyTunnelAnswer(Answer ans, long from, long to, long networkId) {
        if (ans.getResult()) {
            OvsTunnelNetworkVO lock = _tunnelNetworkDao.acquireInLockTable(Long.valueOf(1));
            if (lock == null) {
                s_logger.warn(String.format("failed to lock" +
                        "ovs_tunnel_account, remove record of " +
                        "tunnel(from=%1$s, to=%2$s account=%3$s) failed",
                        from, to, networkId));
                return;
            }

            _tunnelNetworkDao.removeByFromToNetwork(from, to, networkId);
            _tunnelNetworkDao.releaseFromLockTable(lock.getId());

            s_logger.debug(String.format("Destroy tunnel(account:%1$s," +
                    "from:%2$s, to:%3$s) successful",
                    networkId, from, to));
        } else {
            s_logger.debug(String.format("Destroy tunnel(account:%1$s," + "from:%2$s, to:%3$s) failed", networkId, from, to));
        }
    }

};