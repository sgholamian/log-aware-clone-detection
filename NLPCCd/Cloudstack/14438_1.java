//,temp,sample_1595.java,2,13,temp,sample_651.java,2,13
//,2
public class xxx {
private static String encodeParam(String value) {
if (!ApiServer.isEncodeApiResponse()) {
return value;
}
try {
return new URLEncoder().encode(value).replaceAll("\\+", "%20");
} catch (Exception e) {


log.info("unable to encode");
}
}

};