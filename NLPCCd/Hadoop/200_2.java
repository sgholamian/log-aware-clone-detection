//,temp,sample_6840.java,2,9,temp,sample_1639.java,2,10
//,3
public class xxx {
protected void setResponseHeaders(HttpResponse response, boolean keepAliveParam, long contentLength) {
if (!connectionKeepAliveEnabled && !keepAliveParam) {
if (LOG.isDebugEnabled()) {


log.info("setting connection close header");
}
}
}

};