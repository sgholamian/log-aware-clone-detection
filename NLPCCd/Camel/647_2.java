//,temp,sample_4466.java,2,12,temp,sample_8026.java,2,10
//,3
public class xxx {
private synchronized void setLeader(boolean isLeader) {
if (isLeader && leader.compareAndSet(false, isLeader)) {
startManagedRoutes();
} else if (!isLeader && leader.getAndSet(isLeader)) {


log.info("leadership lost");
}
}

};