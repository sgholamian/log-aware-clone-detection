//,temp,sample_4856.java,2,19,temp,sample_4723.java,2,19
//,3
public class xxx {
public void dummy_method(){
String sessionId = conf.get("llap.daemon.metrics.sessionid");
this.cacheMetrics = LlapDaemonCacheMetrics.create(displayName, sessionId);
displayName = "LlapDaemonIOMetrics-" + MetricsUtils.getHostName();
String[] strIntervals = HiveConf.getTrimmedStringsVar(conf, HiveConf.ConfVars.LLAP_IO_DECODING_METRICS_PERCENTILE_INTERVALS);
List<Integer> intervalList = new ArrayList<>();
if (strIntervals != null) {
for (String strInterval : strIntervals) {
try {
intervalList.add(Integer.valueOf(strInterval));
} catch (NumberFormatException e) {


log.info("ignoring io decoding metrics interval from as it is invalid");
}
}
}
}

};