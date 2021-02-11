//,temp,sample_2637.java,2,15,temp,sample_2636.java,2,13
//,3
public class xxx {
private void startActiveMasterManager(int infoPort) throws KeeperException {
String backupZNode = ZNodePaths.joinZNode( zooKeeper.znodePaths.backupMasterAddressesZNode, serverName.toString());
if (!MasterAddressTracker.setMasterAddress(zooKeeper, backupZNode, serverName, infoPort)) {
}
this.activeMasterManager.setInfoPort(infoPort);
int timeout = conf.getInt(HConstants.ZK_SESSION_TIMEOUT, HConstants.DEFAULT_ZK_SESSION_TIMEOUT);
if (conf.getBoolean(HConstants.MASTER_TYPE_BACKUP, HConstants.DEFAULT_MASTER_TYPE_BACKUP)) {
while (!activeMasterManager.hasActiveMaster()) {


log.info("waiting for master address and cluster state znode to be written");
}
}
}

};