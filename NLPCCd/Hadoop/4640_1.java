//,temp,sample_6434.java,2,9,temp,sample_9605.java,2,13
//,3
public class xxx {
private StringRequestEntity getAuthenticationRequst(AuthenticationRequest authenticationRequest) throws IOException {
final String data = JSONUtil.toJSON(new AuthenticationRequestWrapper( authenticationRequest));
if (LOG.isDebugEnabled()) {


log.info("authenticating with");
}
}

};