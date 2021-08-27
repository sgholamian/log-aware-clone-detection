//,temp,sample_3987.java,2,18,temp,sample_3986.java,2,16
//,3
public class xxx {
public void serviceStop() throws IOException {
for (int i = 0 ; i < numInstances ; i++) {
if (llapDaemons[i] != null) {
llapDaemons[i].stop();
llapDaemons[i] = null;
}
}
if (ownZkCluster) {
if (miniZooKeeperCluster != null) {


log.info("stopping minizookeeper cluster");
}
}
}

};