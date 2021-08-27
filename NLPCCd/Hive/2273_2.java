//,temp,sample_4643.java,2,11,temp,sample_4642.java,2,11
//,3
public class xxx {
public WMCreateOrUpdateMappingResponse create_or_update_wm_mapping( WMCreateOrUpdateMappingRequest request) throws AlreadyExistsException, NoSuchObjectException, InvalidObjectException, MetaException, TException {
try {
getMS().createOrUpdateWMMapping(request.getMapping(), request.isUpdate());
return new WMCreateOrUpdateMappingResponse();
} catch (MetaException e) {


log.info("exception while trying to create or update wmmapping");
}
}

};