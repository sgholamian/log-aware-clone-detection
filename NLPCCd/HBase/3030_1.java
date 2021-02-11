//,temp,sample_5322.java,2,13,temp,sample_5320.java,2,17
//,3
public class xxx {
public boolean isReady() throws InterruptedException {
int result = -1;
try {
result = ZKUtil.checkExists(watcher, watcher.znodePaths.splitLogZNode);
} catch (KeeperException e) {
}
if (result == -1) {


log.info("znode does not exist waiting for master to create");
}
}

};