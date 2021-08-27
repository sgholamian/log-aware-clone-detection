//,temp,sample_4640.java,2,11,temp,sample_4639.java,2,11
//,3
public class xxx {
public WMCreatePoolResponse create_wm_pool(WMCreatePoolRequest request) throws AlreadyExistsException, NoSuchObjectException, InvalidObjectException, MetaException, TException {
try {
getMS().createPool(request.getPool());
return new WMCreatePoolResponse();
} catch (MetaException e) {


log.info("exception while trying to create wmpool");
}
}

};