//,temp,sample_3154.java,2,13,temp,sample_3040.java,2,13
//,2
public class xxx {
public boolean consolidateVmDisks() throws Exception {
ManagedObjectReference morTask = _context.getService().consolidateVMDisksTask(_mor);
boolean result = _context.getVimClient().waitForTask(morTask);
if (result) {
_context.waitForTaskProgressDone(morTask);
return true;
} else {


log.info("vmware consolidatevmdisks task failed due to");
}
}

};