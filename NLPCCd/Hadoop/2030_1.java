//,temp,sample_4217.java,2,17,temp,sample_7128.java,2,16
//,3
public class xxx {
public void dummy_method(){
MockNM nm1 = rm.registerNode("127.0.0.1:1234", 6 * GB);
RMApp app1 = rm.submitApp(1024);
nm1.nodeHeartbeat(true);
RMAppAttempt attempt1 = app1.getCurrentAppAttempt();
MockAM am1 = rm.sendAMLaunched(attempt1.getAppAttemptId());
am1.registerAppAttempt();
am1.addRequests(new String[] { "127.0.0.1" }, GB, 1, 1);
AllocateResponse alloc1Response = am1.schedule();
nm1.nodeHeartbeat(true);
while (alloc1Response.getAllocatedContainers().size() < 1) {


log.info("waiting for containers to be created for app");
}
}

};