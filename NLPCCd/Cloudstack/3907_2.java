//,temp,sample_4609.java,2,16,temp,sample_4610.java,2,16
//,2
public class xxx {
public void execute() throws ResourceUnavailableException {
try {
boolean result = _rulesService.enableStaticNat(ipAddressId, virtualMachineId, getNetworkId(), getVmSecondaryIp());
if (result) {
SuccessResponse response = new SuccessResponse(getCommandName());
this.setResponseObject(response);
} else {
throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to enable static NAT");
}
} catch (NetworkRuleConflictException ex) {


log.info("network rule conflict");
}
}

};