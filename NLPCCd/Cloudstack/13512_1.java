//,temp,sample_6352.java,2,16,temp,sample_3556.java,2,16
//,3
public class xxx {
public boolean createVm(VirtualMachineConfigSpec vmSpec) throws Exception {
assert (vmSpec != null);
DatacenterMO dcMo = new DatacenterMO(_context, getHyperHostDatacenter());
ManagedObjectReference morPool = getHyperHostOwnerResourcePool();
ManagedObjectReference morTask = _context.getService().createVMTask(dcMo.getVmFolder(), vmSpec, morPool, _mor);
boolean result = _context.getVimClient().waitForTask(morTask);
if (result) {
_context.waitForTaskProgressDone(morTask);
return true;
} else {


log.info("vmware createvm task failed due to");
}
}

};