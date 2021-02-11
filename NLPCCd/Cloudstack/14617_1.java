//,temp,sample_1861.java,2,17,temp,sample_7724.java,2,17
//,2
public class xxx {
public void dummy_method(){
UserVm result;
try {
result = _userVmService.upgradeVirtualMachine(this);
} catch (ResourceUnavailableException ex) {
throw new ServerApiException(ApiErrorCode.RESOURCE_UNAVAILABLE_ERROR, ex.getMessage());
} catch (ConcurrentOperationException ex) {
throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, ex.getMessage());
} catch (ManagementServerException ex) {
throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, ex.getMessage());
} catch (VirtualMachineMigrationException ex) {


log.info("exception");
}
}

};