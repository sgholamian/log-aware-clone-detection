//,temp,sample_7321.java,2,17,temp,sample_5130.java,2,17
//,3
public class xxx {
public void dummy_method(){
MockAM am = rm.sendAMLaunched(attempt.getAppAttemptId());
am.registerAppAttempt();
int request = 13;
am.allocate("h1" , 1000, request, new ArrayList<ContainerId>());
List<Container> conts = am.allocate(new ArrayList<ResourceRequest>(), new ArrayList<ContainerId>()).getAllocatedContainers();
int contReceived = conts.size();
while (contReceived < 3) {
nm1.nodeHeartbeat(true);
conts.addAll(am.allocate(new ArrayList<ResourceRequest>(), new ArrayList<ContainerId>()).getAllocatedContainers());
contReceived = conts.size();


log.info("got containers waiting to get");
}
}

};