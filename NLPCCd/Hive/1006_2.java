//,temp,sample_3180.java,2,18,temp,sample_3179.java,2,11
//,3
public class xxx {
private void processCreateResult(CuratorFramework client, CuratorEvent event) throws Exception {
boolean doesExist = event.getResultCode() == KeeperException.Code.NODEEXISTS.intValue();
if (!doesExist && event.getResultCode() != KeeperException.Code.OK.intValue()) {
if (LOG.isInfoEnabled()) {


log.info("trying to reacquire due to create error");
}
}
}

};