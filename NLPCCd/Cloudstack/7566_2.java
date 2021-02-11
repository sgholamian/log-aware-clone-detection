//,temp,sample_9161.java,2,18,temp,sample_4391.java,2,18
//,2
public class xxx {
public void dummy_method(){
for (HostVO neighbor : neighbors) {
if (neighbor.getId() == agent.getId() || neighbor.getHypervisorType() != Hypervisor.HypervisorType.Simulator) {
continue;
}
try {
Answer answer = _agentMgr.easySend(neighbor.getId(), cmd);
if (answer != null) {
return answer.getResult() ? Status.Up : Status.Down;
}
} catch (Exception e) {


log.info("failed to send command to host");
}
}
}

};