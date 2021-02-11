//,temp,sample_8884.java,2,12,temp,sample_5439.java,2,16
//,3
public class xxx {
public void setup() throws Exception {
zkc = connectZooKeeper(HOSTPORT);
try {
ZKUtil.deleteRecursive(zkc, BK_ROOT_PATH);
} catch (KeeperException.NoNodeException e) {
} catch (Exception e) {


log.info("exception when deleting bookie root path in zk");
}
}

};