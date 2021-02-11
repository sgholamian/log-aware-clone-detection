//,temp,sample_7723.java,2,15,temp,sample_1860.java,2,15
//,2
public class xxx {
public void execute(){
UserVm result;
try {
result = _userVmService.upgradeVirtualMachine(this);
} catch (ResourceUnavailableException ex) {
throw new ServerApiException(ApiErrorCode.RESOURCE_UNAVAILABLE_ERROR, ex.getMessage());
} catch (ConcurrentOperationException ex) {
throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, ex.getMessage());
} catch (ManagementServerException ex) {


log.info("exception");
}
}

};