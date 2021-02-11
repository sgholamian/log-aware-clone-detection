//,temp,sample_2810.java,2,15,temp,sample_2388.java,2,15
//,3
public class xxx {
protected void emitMetric(String name, String type,  String value) throws IOException {
if (name == null) {
return;
} else if (value == null) {
return;
} else if (type == null) {
return;
}
if (LOG.isDebugEnabled()) {


log.info("emitting metric type value from hostname");
}
}

};