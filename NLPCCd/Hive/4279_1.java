//,temp,sample_2368.java,2,16,temp,sample_2367.java,2,12
//,3
public class xxx {
private String doKerberosAuth(HttpServletRequest request) throws HttpAuthenticationException {
if (httpUGI != null) {
try {
return httpUGI.doAs(new HttpKerberosServerAction(request, httpUGI));
} catch (Exception e) {
}
}
try {
return serviceUGI.doAs(new HttpKerberosServerAction(request, serviceUGI));
} catch (Exception e) {


log.info("failed to authenticate with hive host kerberos principal");
}
}

};