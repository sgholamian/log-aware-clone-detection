//,temp,sample_5269.java,2,16,temp,sample_5270.java,2,15
//,3
public class xxx {
private synchronized void warnStuckTasks() {
if (rpcWarnTime > 0) {
final long now = EnvironmentEdgeManager.currentTime();
for (Iterator<TaskAndWeakRefPair> it = rpcTasks.iterator();
it.hasNext();) {
TaskAndWeakRefPair pair = it.next();
MonitoredTask stat = pair.get();
if ((stat.getState() == MonitoredTaskImpl.State.RUNNING) && (now >= stat.getWarnTime() + rpcWarnTime)) {


log.info("task may be stuck");
}
}
}
}

};