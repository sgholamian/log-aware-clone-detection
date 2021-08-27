//,temp,sample_4676.java,2,18,temp,sample_4678.java,2,18
//,2
public class xxx {
public void dummy_method(){
final Optional<String> newLeader = leader;
if (!newLeader.equals(lastCommunicatedLeader)) {
lastCommunicatedLeader = newLeader;
try {
handler.onKubernetesClusterEvent(new KubernetesClusterEvent.KubernetesClusterLeaderChangedEvent() {
public Optional<String> getData() {
return newLeader;
}
});
} catch (Throwable t) {


log.info("error while communicating the new leader to the handler");
}
}
}

};