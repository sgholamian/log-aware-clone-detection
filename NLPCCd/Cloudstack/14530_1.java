//,temp,sample_7721.java,2,11,temp,sample_1858.java,2,11
//,2
public class xxx {
public void execute() {
UserVm result;
try {
result = _userVmService.upgradeVirtualMachine(this);
} catch (ResourceUnavailableException ex) {


log.info("exception");
}
}

};