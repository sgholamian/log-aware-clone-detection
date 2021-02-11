//,temp,sample_9193.java,2,13,temp,sample_9194.java,2,13
//,3
public class xxx {
public boolean rename(String newName) throws Exception {
ManagedObjectReference morTask = _context.getService().renameTask(_mor, newName);
boolean result = _context.getVimClient().waitForTask(morTask);
if (result) {
_context.waitForTaskProgressDone(morTask);
return true;
} else {


log.info("vmware rename task failed due to");
}
}

};