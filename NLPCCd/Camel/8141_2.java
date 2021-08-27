//,temp,sample_2219.java,2,13,temp,sample_2220.java,2,14
//,2
public class xxx {
protected void doStart() throws Exception {
super.doStart();
String verb = getEndpoint().getVerb();
String path = getEndpoint().getPath();
String accept = getEndpoint().getAccept();
boolean matchOnUriPrefix = getEndpoint().getSparkConfiguration().isMatchOnUriPrefix();
if (accept != null) {
} else {


log.info("spark rest");
}
}

};