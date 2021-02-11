//,temp,sample_3077.java,2,18,temp,sample_3074.java,2,18
//,3
public class xxx {
public void dummy_method(){
synchronized (_mor.getValue().intern()) {
VirtualDevice newDisk = VmwareHelper.prepareDiskDevice(this, null, getScsiDeviceControllerKey(), vmdkDatastorePathChain, morDs, -1, 1);
VirtualMachineConfigSpec reConfigSpec = new VirtualMachineConfigSpec();
VirtualDeviceConfigSpec deviceConfigSpec = new VirtualDeviceConfigSpec();
deviceConfigSpec.setDevice(newDisk);
deviceConfigSpec.setOperation(VirtualDeviceConfigSpecOperation.ADD);
reConfigSpec.getDeviceChange().add(deviceConfigSpec);
ManagedObjectReference morTask = _context.getService().reconfigVMTask(_mor, reConfigSpec);
boolean result = _context.getVimClient().waitForTask(morTask);
if (!result) {


log.info("vcenter api trace attachdisk done failed");
}
}
}

};