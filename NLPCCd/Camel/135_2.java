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
boolean acquired = tryAcquireLeadership();
if (acquired) {


log.info("leadership acquired by current pod with immediate effect");
}
}
}

};