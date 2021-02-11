//,temp,sample_3604.java,2,17,temp,sample_3590.java,2,13
//,3
public class xxx {
public void dummy_method(){
try {
byte [] data = zkw.getRecoverableZooKeeper().getData(znode, zkw, stat);
logRetrievedMsg(zkw, znode, data, watcherSet);
return data;
} catch (KeeperException.NoNodeException e) {
return null;
} catch (KeeperException e) {
zkw.keeperException(e);
return null;
} catch (InterruptedException e) {


log.info("unable to get data of znode");
}
}

};