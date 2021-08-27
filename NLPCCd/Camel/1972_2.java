//,temp,sample_4676.java,2,18,temp,sample_4678.java,2,18
//,2
public class xxx {
public void dummy_method(){
final Set<String> newMembers = members;
if (!newMembers.equals(lastCommunicatedMembers)) {
lastCommunicatedMembers = newMembers;
try {
handler.onKubernetesClusterEvent(new KubernetesClusterEvent.KubernetesClusterMemberListChangedEvent() {
public Set<String> getData() {
return newMembers;
}
});
} catch (Throwable t) {


log.info("error while communicating the cluster members to the handler");
}
}
}

};