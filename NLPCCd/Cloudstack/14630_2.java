//,temp,sample_5768.java,2,17,temp,sample_3329.java,2,17
//,2
public class xxx {
public void dummy_method(){
try {
if (isStart()) {
_vpcService.startVpc(getEntityId(), true);
} else {
s_logger.debug("Not starting VPC as " + ApiConstants.START + "=false was passed to the API");
}
vpc = _entityMgr.findById(Vpc.class, getEntityId());
} catch (ResourceUnavailableException ex) {
throw new ServerApiException(ApiErrorCode.RESOURCE_UNAVAILABLE_ERROR, ex.getMessage());
} catch (ConcurrentOperationException ex) {


log.info("exception");
}
}

};