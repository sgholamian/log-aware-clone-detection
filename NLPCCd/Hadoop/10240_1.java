//,temp,sample_7296.java,2,18,temp,sample_7295.java,6,16
//,3
public class xxx {
public void dummy_method(){
class VolRemoveThread extends Thread {
public void run() {
Set<File> volumesToRemove = new HashSet<>();
try {
volumesToRemove.add(StorageLocation.parse( dataset.getVolume(eb).getBasePath()).getFile());
} catch (Exception e) {
Assert.fail("Exception in remove volume thread, check log for " + "details.");
}
dataset.removeVolumes(volumesToRemove, true);
volRemoveCompletedLatch.countDown();


log.info("removed volume");
}
}
}

};