//,temp,sample_7602.java,2,17,temp,sample_7603.java,2,18
//,2
public class xxx {
public void dummy_method(){
try {
appLogs.parseSummaryLogs();
if (appLogs.isDone()) {
appLogs.moveToDone();
appIdLogMap.remove(appLogs.getAppId());
}
} catch (Exception e) {
Throwable t = extract(e);
if (t instanceof InterruptedException) {
} else {


log.info("error processing logs for");
}
}
}

};