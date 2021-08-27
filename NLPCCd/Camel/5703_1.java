//,temp,sample_7379.java,2,16,temp,sample_7378.java,2,13
//,3
public class xxx {
public HttpClient createHttpClient() {
ObjectHelper.notNull(clientParams, "clientParams");
ObjectHelper.notNull(httpConnectionManager, "httpConnectionManager");
HttpClient answer = new HttpClient(getClientParams());
if (ObjectHelper.isNotEmpty(getCamelContext().getProperty("http.proxyHost")) && ObjectHelper.isNotEmpty(getCamelContext().getProperty("http.proxyPort"))) {
String host = getCamelContext().getProperty("http.proxyHost");
int port = Integer.parseInt(getCamelContext().getProperty("http.proxyPort"));
answer.getHostConfiguration().setProxy(host, port);
}
if (getProxyHost() != null) {


log.info("using proxy");
}
}

};