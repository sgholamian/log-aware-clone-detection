//,temp,sample_1608.java,2,18,temp,sample_4391.java,2,18
//,2
public class xxx {
public void dummy_method(){
for (HostVO neighbor : neighbors) {
if (neighbor.getId() == agent.getId() || neighbor.getHypervisorType() != Hypervisor.HypervisorType.Ovm3) {
continue;
}
try {
Answer answer = agentMgr.easySend(neighbor.getId(), cmd);
if (answer != null) {
return answer.getResult() ? Status.Down : Status.Up;
}
} catch (Exception e) {


log.info("failed to send command to host");
}
}
}

};