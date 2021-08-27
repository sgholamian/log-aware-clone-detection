//,temp,sample_7757.java,2,17,temp,sample_7759.java,2,17
//,2
public class xxx {
public void dummy_method(){
String rawPath = getRawPath(request);
headers.put(Exchange.HTTP_METHOD, request.getMethod());
headers.put(Exchange.HTTP_QUERY, request.getQueryString());
headers.put(Exchange.HTTP_URL, request.getRequestURL().toString());
headers.put(Exchange.HTTP_URI, request.getRequestURI());
headers.put(Exchange.HTTP_PATH, rawPath);
if (!headers.containsKey(Exchange.CONTENT_TYPE)) {
headers.put(Exchange.CONTENT_TYPE, request.getContentType());
}
if (LOG.isTraceEnabled()) {


log.info("http uri");
}
}

};