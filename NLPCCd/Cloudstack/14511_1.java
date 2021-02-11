//,temp,sample_1356.java,2,10,temp,sample_1355.java,2,10
//,2
public class xxx {
private HashMap<String, PowerState> getVmStates() throws Exception {
VmwareHypervisorHost hyperHost = getHyperHost(getServiceContext());
int key = ((HostMO)hyperHost).getCustomFieldKey("VirtualMachine", CustomFieldConstants.CLOUD_VM_INTERNAL_NAME);
if (key == 0) {


log.info("custom field is not registered");
}
}

};