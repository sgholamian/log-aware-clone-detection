//,temp,sample_3056.java,2,17,temp,sample_3055.java,2,17
//,3
public class xxx {
public void dummy_method(){
cloneSpec.setPowerOn(false);
cloneSpec.setTemplate(false);
cloneSpec.setLocation(rSpec);
cloneSpec.setSnapshot(morBaseSnapshot);
ManagedObjectReference morTask = _context.getService().cloneVMTask(_mor, morFolder, cloneName, cloneSpec);
boolean result = _context.getVimClient().waitForTask(morTask);
if (result) {
_context.waitForTaskProgressDone(morTask);
return true;
} else {


log.info("vmware clonevm task failed due to");
}
}

};