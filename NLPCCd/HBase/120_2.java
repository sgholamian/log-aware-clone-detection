//,temp,sample_2184.java,2,8,temp,sample_3409.java,2,10
//,3
public class xxx {
public synchronized void addKey(AuthenticationKey key) throws IOException {
if (leaderElector.isMaster()) {
if (LOG.isDebugEnabled()) {


log.info("running as master ignoring new key");
}
}
}

};