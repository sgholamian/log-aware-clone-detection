//,temp,sample_2341.java,2,11,temp,sample_3012.java,2,18
//,3
public class xxx {
public synchronized void reconnectAfterExpiration() throws IOException, KeeperException, InterruptedException {
if (zk != null) {
zk.close();
zk = null;
}
checkZk();


log.info("recreated a zookeeper session is");
}

};