//,temp,sample_5362.java,2,16,temp,sample_1850.java,2,16
//,2
public class xxx {
public void execute() {
UserVm result;
if (getStartVm()) {
try {
CallContext.current().setEventDetails("Vm Id: " + getEntityId());
result = _userVmService.startVirtualMachine(this);
} catch (ResourceUnavailableException ex) {
throw new ServerApiException(ApiErrorCode.RESOURCE_UNAVAILABLE_ERROR, ex.getMessage());
} catch (ConcurrentOperationException ex) {


log.info("exception");
}
}
}

};