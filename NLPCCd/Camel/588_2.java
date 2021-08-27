//,temp,sample_989.java,2,15,temp,sample_988.java,2,14
//,3
public class xxx {
protected void doStop() throws Exception {
try {
super.doStop();
} finally {
final HttpClient httpClient = getConfiguration().getHttpClient();
if (httpClient != null && getComponent().getConfig().getHttpClient() != httpClient) {
final String endpointUri = getEndpointUri();


log.info("stopping http client for");
}
}
}

};