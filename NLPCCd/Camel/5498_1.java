//,temp,sample_8113.java,2,15,temp,sample_8114.java,2,19
//,3
public class xxx {
public void groupEvent(Group group, GroupEvent event) {
switch (event) {
case CONNECTED: break;
case CHANGED: if (singleton.isConnected()) {
if (singleton.isMaster()) {
if (LOG.isDebugEnabled()) {


log.info("master standby endpoint is master for in");
}
}
}
}
}

};