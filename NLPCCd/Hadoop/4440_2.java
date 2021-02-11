//,temp,sample_2675.java,2,11,temp,sample_7905.java,2,14
//,3
public class xxx {
protected void tearDownAll() throws Exception {
synchronized (this) {
if (allClients != null) for (ZooKeeper zk : allClients) {
try {
if (zk != null) zk.close();
} catch (InterruptedException e) {


log.info("ignoring interrupt");
}
}
}
}

};