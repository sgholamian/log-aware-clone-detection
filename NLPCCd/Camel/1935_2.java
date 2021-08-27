//,temp,sample_5442.java,2,13,temp,sample_1446.java,2,12
//,2
public class xxx {
public void onResponse(T response, Map<String, String> responseHeaders) {
this.response = response;
if (LOG.isDebugEnabled()) {
if (response instanceof ODataFeed) {
} else if (response instanceof ODataEntry) {


log.info("received response");
}
}
}

};