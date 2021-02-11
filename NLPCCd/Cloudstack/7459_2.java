//,temp,sample_1849.java,2,14,temp,sample_5361.java,2,14
//,2
public class xxx {
public void execute(){
UserVm result;
if (getStartVm()) {
try {
CallContext.current().setEventDetails("Vm Id: "+getEntityId());
result = _userVmService.startVirtualMachine(this);
} catch (ResourceUnavailableException ex) {


log.info("exception");
}
}
}

};