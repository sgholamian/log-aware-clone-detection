//,temp,sample_1804.java,2,17,temp,sample_7132.java,2,17
//,3
public class xxx {
public void dummy_method(){
am1.addRequests(new String[] { "127.0.0.1", "127.0.0.2" }, GB, 1, 1);
AllocateResponse alloc1Response = am1.schedule();
am2.addRequests(new String[] { "127.0.0.1", "127.0.0.2" }, 3 * GB, 0, 1);
AllocateResponse alloc2Response = am2.schedule();
nm1.nodeHeartbeat(true);
while (alloc1Response.getAllocatedContainers().size() < 1) {
Thread.sleep(1000);
alloc1Response = am1.schedule();
}
while (alloc2Response.getAllocatedContainers().size() < 1) {


log.info("waiting for containers to be created for app");
}
}

};