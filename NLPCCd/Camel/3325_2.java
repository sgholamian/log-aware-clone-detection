//,temp,sample_7720.java,2,12,temp,sample_1445.java,2,11
//,3
public class xxx {
public void onResponse(T response, Map<String, String> responseHeaders) {
this.response = response;
if (LOG.isDebugEnabled()) {
if (response instanceof ODataFeed) {


log.info("received response");
}
}
}

};