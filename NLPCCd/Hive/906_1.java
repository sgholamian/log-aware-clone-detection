//,temp,sample_493.java,2,17,temp,sample_491.java,2,17
//,3
public class xxx {
private boolean initMetricsReporter() {
List<String> metricsReporterNames = Lists.newArrayList(Splitter.on(",").trimResults(). omitEmptyStrings().split(conf.getVar(HiveConf.ConfVars.HIVE_METRICS_REPORTER)));
if (metricsReporterNames.isEmpty()) {
return false;
}
MetricsReporting reporter = null;
for (String metricsReportingName : metricsReporterNames) {
try {
reporter = MetricsReporting.valueOf(metricsReportingName.trim().toUpperCase());
} catch (IllegalArgumentException e) {


log.info("invalid reporter name");
}
}
}

};