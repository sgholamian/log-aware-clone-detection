//,temp,sample_6318.java,2,16,temp,sample_6317.java,2,15
//,2
public class xxx {
private void handleDestroyTunnelAnswer(Answer ans, long from, long to, long networkId) {
if (ans.getResult()) {
OvsTunnelNetworkVO lock = _tunnelNetworkDao.acquireInLockTable(Long.valueOf(1));
if (lock == null) {
s_logger.warn(String.format("failed to lock" + "ovs_tunnel_account, remove record of " + "tunnel(from=%1$s, to=%2$s account=%3$s) failed", from, to, networkId));
return;
}
_tunnelNetworkDao.removeByFromToNetwork(from, to, networkId);
_tunnelNetworkDao.releaseFromLockTable(lock.getId());
} else {


log.info("destroy tunnel account s from s to s failed");
}
}

};