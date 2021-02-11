//,temp,sample_9193.java,2,13,temp,sample_3040.java,2,13
//,2
public class xxx {
public boolean destroy() throws Exception {
ManagedObjectReference morTask = _context.getService().destroyTask(_mor);
boolean result = _context.getVimClient().waitForTask(morTask);
if (result) {
_context.waitForTaskProgressDone(morTask);
return true;
} else {


log.info("vmware destroy task failed due to");
}
}

};