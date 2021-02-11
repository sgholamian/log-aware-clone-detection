//,temp,sample_3590.java,2,13,temp,sample_3605.java,2,12
//,3
public class xxx {
public static byte [] getDataNoWatch(ZKWatcher zkw, String znode, Stat stat) throws KeeperException {
try {
byte [] data = zkw.getRecoverableZooKeeper().getData(znode, null, stat);
logRetrievedMsg(zkw, znode, data, false);
return data;
} catch (KeeperException.NoNodeException e) {


log.info("unable to get data of znode because node does not exist not necessarily an error");
}
}

};