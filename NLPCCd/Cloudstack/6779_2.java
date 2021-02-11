//,temp,sample_3043.java,2,13,temp,sample_3042.java,2,13
//,3
public class xxx {
public boolean changeDatastore(VirtualMachineRelocateSpec relocateSpec) throws Exception {
ManagedObjectReference morTask = _context.getVimClient().getService().relocateVMTask(_mor, relocateSpec, VirtualMachineMovePriority.DEFAULT_PRIORITY);
boolean result = _context.getVimClient().waitForTask(morTask);
if (result) {
_context.waitForTaskProgressDone(morTask);
return true;
} else {


log.info("vmware relocatevm task to change datastore failed due to");
}
}

};