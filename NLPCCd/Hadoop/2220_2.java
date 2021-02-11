//,temp,sample_510.java,2,19,temp,sample_509.java,2,19
//,3
public class xxx {
public void dummy_method(){
ugi = UserGroupInformation.createRemoteUser(realUser, handlerAuthMethod);
String doAsUser = getDoAs(request);
if (doAsUser != null) {
ugi = UserGroupInformation.createProxyUser(doAsUser, ugi);
try {
ProxyUsers.authorize(ugi, request.getRemoteAddr());
} catch (AuthorizationException ex) {
HttpExceptionUtils.createServletExceptionResponse(response, HttpServletResponse.SC_FORBIDDEN, ex);
requestCompleted = true;
if (LOG.isDebugEnabled()) {


log.info("authentication exception");
}
}
}
}

};