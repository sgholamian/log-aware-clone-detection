//,temp,sample_5269.java,2,16,temp,sample_5270.java,2,15
//,3
public class xxx {
private synchronized void purgeExpiredTasks() {
for (Iterator<TaskAndWeakRefPair> it = tasks.iterator();
it.hasNext();) {
TaskAndWeakRefPair pair = it.next();
MonitoredTask stat = pair.get();
if (pair.isDead()) {
if (stat.getState() == MonitoredTaskImpl.State.RUNNING) {


log.info("status appears to have been leaked");
}
}
}
}

};