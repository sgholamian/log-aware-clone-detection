//,temp,sample_4633.java,2,10,temp,sample_4641.java,2,11
//,3
public class xxx {
public WMDropPoolResponse drop_wm_pool(WMDropPoolRequest request) throws NoSuchObjectException, InvalidOperationException, MetaException, TException {
try {
getMS().dropWMPool(request.getResourcePlanName(), request.getPoolPath());
return new WMDropPoolResponse();
} catch (MetaException e) {


log.info("exception while trying to drop wmpool");
}
}

};