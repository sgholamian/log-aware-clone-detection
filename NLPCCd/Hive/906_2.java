//,temp,sample_493.java,2,17,temp,sample_491.java,2,17
//,3
public class xxx {
private boolean initCodahaleMetricsReporterClasses() {
List<String> reporterClasses = Lists.newArrayList(Splitter.on(",").trimResults(). omitEmptyStrings().split(conf.getVar(HiveConf.ConfVars.HIVE_CODAHALE_METRICS_REPORTER_CLASSES)));
if (reporterClasses.isEmpty()) {
return false;
}
for (String reporterClass : reporterClasses) {
Class name = null;
try {
name = conf.getClassByName(reporterClass);
} catch (ClassNotFoundException e) {


log.info("unable to instantiate metrics reporter class from conf hive codahale metrics reporter classes");
}
}
}

};