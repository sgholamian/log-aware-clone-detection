//,temp,sample_6434.java,2,9,temp,sample_9365.java,2,14
//,3
public class xxx {
private StringRequestEntity getAuthenticationRequst(AuthenticationRequest authenticationRequest) throws IOException {
final String data = JSONUtil.toJSON(new AuthenticationRequestWrapper( authenticationRequest));
if (LOG.isDebugEnabled()) {


log.info("authenticating with");
}
}

};