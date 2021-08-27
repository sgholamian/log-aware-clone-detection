//,temp,sample_4856.java,2,19,temp,sample_4723.java,2,19
//,3
public class xxx {
public void dummy_method(){
LlapDaemonJvmMetrics.create(displayNameJvm, sessionId, daemonConf);
String displayName = "LlapDaemonExecutorMetrics-" + hostName;
daemonConf.set("llap.daemon.metrics.sessionid", sessionId);
String[] strIntervals = HiveConf.getTrimmedStringsVar(daemonConf, HiveConf.ConfVars.LLAP_DAEMON_TASK_PREEMPTION_METRICS_INTERVALS);
List<Integer> intervalList = new ArrayList<>();
if (strIntervals != null) {
for (String strInterval : strIntervals) {
try {
intervalList.add(Integer.valueOf(strInterval));
} catch (NumberFormatException e) {


log.info("ignoring task pre emption metrics interval from as it is invalid");
}
}
}
}

};