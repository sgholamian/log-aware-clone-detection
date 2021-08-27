//,temp,sample_4640.java,2,11,temp,sample_4639.java,2,11
//,3
public class xxx {
public WMAlterPoolResponse alter_wm_pool(WMAlterPoolRequest request) throws AlreadyExistsException, NoSuchObjectException, InvalidObjectException, MetaException, TException {
try {
getMS().alterPool(request.getPool(), request.getPoolPath());
return new WMAlterPoolResponse();
} catch (MetaException e) {


log.info("exception while trying to alter wmpool");
}
}

};