//,temp,sample_9448.java,2,11,temp,sample_9449.java,2,11
//,2
public class xxx {
protected synchronized void serviceStart() throws Exception {
startResourceManager(index);
if(index == 0 && resourceManagers[index].getRMContext().isHAEnabled()) {
resourceManagers[index].getRMContext().getRMAdminService() .transitionToActive(new HAServiceProtocol.StateChangeRequestInfo( HAServiceProtocol.RequestSource.REQUEST_BY_USER_FORCED));
}
Configuration conf = resourceManagers[index].getConfig();


log.info("miniyarn resourcemanager address");
}

};