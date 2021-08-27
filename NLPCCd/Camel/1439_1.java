//,temp,sample_1634.java,2,18,temp,sample_1635.java,2,18
//,3
public class xxx {
public void dummy_method(){
boolean inRole = true;
if (principal != null) {
subject = authenticate(security.getSecurityAuthenticator(), security.getLoginDeniedLoggingLevel(), principal);
if (subject != null) {
String userRoles = security.getSecurityAuthenticator().getUserRoles(subject);
inRole = matchesRoles(roles, userRoles);
}
}
if (principal == null || subject == null || !inRole) {
if (principal == null) {


log.info("http basic auth required for resource");
}
}
}

};