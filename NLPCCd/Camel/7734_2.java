//,temp,sample_1653.java,2,16,temp,sample_2719.java,2,15
//,3
public class xxx {
protected void doStart() throws Exception {
super.doStart();
String path = endpoint.getComponent().getCamelClusterPath(endpoint.getGroupName());
this.groupListener = new ZookeeperGroupListenerSupport(path, endpoint, onLockOwned(), onDisconnected());
this.groupListener.setCamelContext(endpoint.getCamelContext());
this.groupListener.setZooKeeperUrl(endpoint.getComponent().getZooKeeperUrl());
this.groupListener.setZooKeeperPassword(endpoint.getComponent().getZooKeeperPassword());
this.groupListener.setCurator(endpoint.getComponent().getCurator());
this.groupListener.setMaximumConnectionTimeout(endpoint.getComponent().getMaximumConnectionTimeout());
ServiceHelper.startService(groupListener);


log.info("attempting to become master for endpoint in with singletonid");
}

};