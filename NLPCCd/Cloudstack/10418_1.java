//,temp,sample_9275.java,2,17,temp,sample_1689.java,2,17
//,3
public class xxx {
public void dummy_method(){
boolean result = false;
try {
result = _autoScaleService.deleteCondition(getId());
} catch (ResourceInUseException ex) {
throw new ServerApiException(ApiErrorCode.RESOURCE_IN_USE_ERROR, ex.getMessage());
}
if (result) {
SuccessResponse response = new SuccessResponse(getCommandName());
setResponseObject(response);
} else {


log.info("failed to delete condition");
}
}

};