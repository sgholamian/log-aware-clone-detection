//,temp,sample_1144.java,2,12,temp,sample_1143.java,2,11
//,2
public class xxx {
private void dumpThreads() {
ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
for (ThreadInfo threadInfo : threadMXBean.getThreadInfo(threadMXBean.getAllThreadIds(), Integer.MAX_VALUE)) {
if (Thread.State.BLOCKED.equals(threadInfo.getThreadState())) {
} else {


log.info("normal thread");
}
}
}

};