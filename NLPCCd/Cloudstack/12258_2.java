//,temp,sample_2503.java,2,13,temp,sample_2504.java,2,14
//,3
public class xxx {
private Socket _getSocket() throws IOException {
if (useSSL) {
SSLContext context = null;
try {
context = SSLUtils.getSSLContext("SunJSSE");
} catch (NoSuchAlgorithmException e) {
} catch (NoSuchProviderException e) {


log.info("unexpected exception");
}
}
}

};