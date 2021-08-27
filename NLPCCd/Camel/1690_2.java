//,temp,sample_5231.java,2,17,temp,sample_7060.java,2,17
//,3
public class xxx {
public void dummy_method(){
String path = uri.getRawPath();
if (configuration.getPath() != null) {
String matchPath = path.toLowerCase(Locale.US);
String match = configuration.getPath() != null ? configuration.getPath().toLowerCase(Locale.US) : null;
if (match != null && matchPath.startsWith(match)) {
path = path.substring(configuration.getPath().length());
}
}
headers.put(Exchange.HTTP_PATH, path);
if (LOG.isTraceEnabled()) {


log.info("http method");
}
}

};