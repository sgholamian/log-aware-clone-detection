//,temp,sample_3034.java,2,13,temp,sample_3035.java,2,16
//,3
public class xxx {
private void refreshStatusNotLeader() {
boolean pulled = lookupNewLeaderInfo();
if (!pulled) {
rescheduleAfterDelay();
return;
}
if (this.latestLeaderInfo.hasEmptyLeader()) {


log.info("the cluster has no leaders trying to acquire the leadership");
}
}

};