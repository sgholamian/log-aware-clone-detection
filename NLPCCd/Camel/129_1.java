//,temp,sample_3733.java,2,15,temp,sample_2405.java,2,18
//,3
public class xxx {
public void closeConnection() {
try {
if (zookeeper != null) {
zookeeper.close();
zookeeper = null;
}
if (LOG.isDebugEnabled()) {
}
} catch (InterruptedException e) {


log.info("error closing zookeeper connection this exception will be ignored");
}
}

};