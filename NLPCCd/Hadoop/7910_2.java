//,temp,sample_1804.java,2,17,temp,sample_7132.java,2,17
//,3
public class xxx {
public void dummy_method(){
nm1.nodeHeartbeat(true);
int waitCounter = 20;
while (alloc1Response.getAllocatedContainers().size() < 1 && waitCounter-- > 0) {
Thread.sleep(500);
alloc1Response = am1.schedule();
}
Assert.assertTrue(alloc1Response.getAllocatedContainers().size() == 0);
waitCounter = 20;
nm2.nodeHeartbeat(true);
while (alloc1Response.getAllocatedContainers().size() < 1 && waitCounter-- > 0) {


log.info("waiting for containers to be created for app");
}
}

};