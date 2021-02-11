//,temp,sample_160.java,2,15,temp,sample_4882.java,2,9
//,3
public class xxx {
protected void emitMetric(String groupName, String name, String type, String value, GangliaConf gConf, GangliaSlope gSlope) throws IOException {
if (name == null) {
return;
} else if (value == null) {
return;
} else if (type == null) {
return;
}
if (LOG.isDebugEnabled()) {


log.info("emitting metric type value slope from hostname");
}
}

};