//,temp,sample_6320.java,2,14,temp,sample_6321.java,2,15
//,2
public class xxx {
private void handleDestroyBridgeAnswer(Answer ans, long hostId, long networkId) {
if (ans.getResult()) {
OvsTunnelNetworkVO lock = _tunnelNetworkDao.acquireInLockTable(Long.valueOf(1));
if (lock == null) {
return;
}
_tunnelNetworkDao.removeByFromNetwork(hostId, networkId);
_tunnelNetworkDao.releaseFromLockTable(lock.getId());


log.info("destroy bridge for network s successful");
}
}

};