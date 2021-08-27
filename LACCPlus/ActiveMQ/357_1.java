//,temp,LDAPAuthorizationMap.java,463,491,temp,LDAPLoginModule.java,493,518
//,3
public class xxx {
    protected DirContext open() throws NamingException {
        if (context != null) {
            return context;
        }

        try {
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, initialContextFactory);
            if (connectionUsername != null && !"".equals(connectionUsername)) {
                env.put(Context.SECURITY_PRINCIPAL, connectionUsername);
            } else {
                throw new NamingException("Empty username is not allowed");
            }
            if (connectionPassword != null && !"".equals(connectionPassword)) {
                env.put(Context.SECURITY_CREDENTIALS, connectionPassword);
            } else {
                throw new NamingException("Empty password is not allowed");
            }
            env.put(Context.SECURITY_PROTOCOL, connectionProtocol);
            env.put(Context.PROVIDER_URL, connectionURL);
            env.put(Context.SECURITY_AUTHENTICATION, authentication);
            context = new InitialDirContext(env);

        } catch (NamingException e) {
            LOG.error(e.toString());
            throw e;
        }
        return context;
    }

};