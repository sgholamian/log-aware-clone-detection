//,temp,sample_7285.java,2,18,temp,sample_7284.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (principal != null) {
subject = authenticate(security.getSecurityAuthenticator(), security.getLoginDeniedLoggingLevel(), principal);
if (subject != null) {
String userRoles = security.getSecurityAuthenticator().getUserRoles(subject);
inRole = matchesRoles(roles, userRoles);
}
}
if (principal == null || subject == null || !inRole) {
if (principal == null) {
} else if (subject == null) {


log.info("http basic auth not authorized for username");
}
}
}

};