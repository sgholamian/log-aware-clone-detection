//,temp,sample_2728.java,2,12,temp,sample_2729.java,2,12
//,2
public class xxx {
public boolean canAuthenticate(final String principal, final String password) {
try {
final LdapContext context = _ldapContextFactory.createUserContext(principal, password);
closeContext(context);
return true;
} catch (NamingException | IOException e) {


log.info("failed to authenticate user incorrect password");
}
}

};