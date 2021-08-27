//,temp,sample_986.java,2,14,temp,sample_987.java,2,15
//,3
public class xxx {
protected void doStart() throws Exception {
try {
super.doStart();
} finally {
final HttpClient httpClient = getConfiguration().getHttpClient();
if (httpClient != null && getComponent().getConfig().getHttpClient() != httpClient) {
final String endpointUri = getEndpointUri();
httpClient.start();


log.info("started http client for");
}
}
}

};