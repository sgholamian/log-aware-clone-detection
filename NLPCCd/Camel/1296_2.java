//,temp,sample_7061.java,2,17,temp,sample_5232.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (endpoint.getHttpURI() != null) {
String endpointPath = endpoint.getHttpURI().getPath();
String matchPath = path.toLowerCase(Locale.US);
String match = endpointPath.toLowerCase(Locale.US);
if (matchPath.startsWith(match)) {
path = path.substring(endpointPath.length());
}
}
headersMap.put(Exchange.HTTP_PATH, path);
if (LOG.isTraceEnabled()) {


log.info("http uri");
}
}

};