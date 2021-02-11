//,temp,sample_3050.java,2,17,temp,sample_3052.java,2,17
//,2
public class xxx {
public void dummy_method(){
ManagedObjectReference morSnapshot = getSnapshotMor(snapshotName);
if (morSnapshot == null) {
return false;
}
ManagedObjectReference morTask = _context.getService().revertToSnapshotTask(morSnapshot, _mor, null);
boolean result = _context.getVimClient().waitForTask(morTask);
if (result) {
_context.waitForTaskProgressDone(morTask);
return true;
} else {


log.info("vmware revert to snapshot failed due to");
}
}

};