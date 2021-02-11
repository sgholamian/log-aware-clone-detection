//,temp,sample_1859.java,2,13,temp,sample_7722.java,2,13
//,2
public class xxx {
public void execute() {
UserVm result;
try {
result = _userVmService.upgradeVirtualMachine(this);
} catch (ResourceUnavailableException ex) {
throw new ServerApiException(ApiErrorCode.RESOURCE_UNAVAILABLE_ERROR, ex.getMessage());
} catch (ConcurrentOperationException ex) {


log.info("exception");
}
}

};