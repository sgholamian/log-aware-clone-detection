//,temp,sample_4643.java,2,11,temp,sample_4642.java,2,11
//,3
public class xxx {
public WMDropMappingResponse drop_wm_mapping(WMDropMappingRequest request) throws NoSuchObjectException, InvalidOperationException, MetaException, TException {
try {
getMS().dropWMMapping(request.getMapping());
return new WMDropMappingResponse();
} catch (MetaException e) {


log.info("exception while trying to drop wmmapping");
}
}

};