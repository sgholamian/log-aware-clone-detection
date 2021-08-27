//,temp,LDAPAuthorizationMap.java,463,491,temp,LDAPLoginModule.java,493,518
//,3
public class xxx {
    protected DirContext open() throws NamingException {
        try {
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, getLDAPPropertyValue(INITIAL_CONTEXT_FACTORY));
            if (isLoginPropertySet(CONNECTION_USERNAME)) {
                env.put(Context.SECURITY_PRINCIPAL, getLDAPPropertyValue(CONNECTION_USERNAME));
            } else {
                throw new NamingException("Empty username is not allowed");
            }

            if (isLoginPropertySet(CONNECTION_PASSWORD)) {
                env.put(Context.SECURITY_CREDENTIALS, getLDAPPropertyValue(CONNECTION_PASSWORD));
            } else {
                throw new NamingException("Empty password is not allowed");
            }
            env.put(Context.SECURITY_PROTOCOL, getLDAPPropertyValue(CONNECTION_PROTOCOL));
            env.put(Context.PROVIDER_URL, getLDAPPropertyValue(CONNECTION_URL));
            env.put(Context.SECURITY_AUTHENTICATION, getLDAPPropertyValue(AUTHENTICATION));
            context = new InitialDirContext(env);

        } catch (NamingException e) {
            log.error(e.toString());
            throw e;
        }
        return context;
    }

};