//,temp,sample_4466.java,2,12,temp,sample_8026.java,2,10
//,3
public class xxx {
protected void setLeader(boolean isLeader) {
if (isLeader && leader.compareAndSet(false, isLeader)) {
startAllStoppedConsumers();
} else {
if (!leader.getAndSet(isLeader) && isLeader) {


log.info("leadership lost");
}
}
}

};