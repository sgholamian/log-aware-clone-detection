//,temp,sample_2481.java,2,17,temp,sample_2369.java,2,17
//,2
public class xxx {
protected void doStart() throws Exception {
super.doStart();
ObjectHelper.notNull(nettyHttpBinding, "nettyHttpBinding", this);
ObjectHelper.notNull(headerFilterStrategy, "headerFilterStrategy", this);
if (securityConfiguration != null) {
ObjectHelper.notEmpty(securityConfiguration.getRealm(), "realm", securityConfiguration);
ObjectHelper.notEmpty(securityConfiguration.getConstraint(), "restricted", securityConfiguration);
if (securityConfiguration.getSecurityAuthenticator() == null) {
JAASSecurityAuthenticator jaas = new JAASSecurityAuthenticator();
jaas.setName(securityConfiguration.getRealm());


log.info("no securityauthenticator configured using jaassecurityauthenticator as authenticator");
}
}
}

};