//,temp,sample_5441.java,2,12,temp,sample_5440.java,2,11
//,3
public class xxx {
public void onResponse(T response, Map<String, String> responseHeaders) {
this.response = response;
if (LOG.isDebugEnabled()) {
if (response instanceof ClientEntitySet) {


log.info("received response");
}
}
}

};