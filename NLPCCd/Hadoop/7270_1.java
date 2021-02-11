//,temp,sample_6434.java,2,9,temp,sample_7163.java,2,10
//,3
public class xxx {
private StringRequestEntity getAuthenticationRequst(AuthenticationRequest authenticationRequest) throws IOException {
final String data = JSONUtil.toJSON(new AuthenticationRequestWrapper( authenticationRequest));
if (LOG.isDebugEnabled()) {


log.info("authenticating with");
}
}

};