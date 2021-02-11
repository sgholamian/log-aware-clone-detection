//,temp,sample_8282.java,2,11,temp,sample_1824.java,2,11
//,2
public class xxx {
public static Element queryAsyncJobResult(String host, InputStream inputStream) {
Element returnBody = null;
Map<String, String> values = getSingleValueFromXML(inputStream, new String[] {"jobid"});
String jobId = values.get("jobid");
if (jobId == null) {


log.info("unable to get a jobid");
}
}

};