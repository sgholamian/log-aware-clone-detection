//,temp,sample_3054.java,2,13,temp,sample_3053.java,2,11
//,3
public class xxx {
private boolean tryAcquireLeadership() {
ConfigMap configMap = this.latestConfigMap;
Set<String> members = this.latestMembers;
LeaderInfo latestLeaderInfo = this.latestLeaderInfo;
if (latestLeaderInfo == null || members == null) {
return false;
} else if (!members.contains(this.lockConfiguration.getPodName())) {


log.info("the list of cluster members does not contain the current pod cannot acquire leadership");
}
}

};