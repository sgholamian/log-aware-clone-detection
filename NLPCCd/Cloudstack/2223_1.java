//,temp,sample_6778.java,2,16,temp,sample_5777.java,2,16
//,3
public class xxx {
public void execute() {
try {
boolean result = _securityGroupService.deleteSecurityGroup(this);
if (result) {
SuccessResponse response = new SuccessResponse(getCommandName());
setResponseObject(response);
} else {
throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to delete security group");
}
} catch (ResourceInUseException ex) {


log.info("exception");
}
}

};