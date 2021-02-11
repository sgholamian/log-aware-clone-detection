//,temp,sample_4217.java,2,17,temp,sample_7128.java,2,16
//,3
public class xxx {
public void dummy_method(){
Assert.assertEquals(2, rm.getRMContext().getRMNodes().size());
RMApp app1 = rm.submitApp(128);
nm1.nodeHeartbeat(true);
RMAppAttempt attempt1 = app1.getCurrentAppAttempt();
MockAM am1 = rm.sendAMLaunched(attempt1.getAppAttemptId());
am1.registerAppAttempt();
am1.addRequests(new String[] {"*"}, 2 * GB, 1, 1);
AllocateResponse alloc1Response = am1.schedule();
nm1.nodeHeartbeat(true);
int waitCounter = 20;


log.info("heartbeating");
}

};