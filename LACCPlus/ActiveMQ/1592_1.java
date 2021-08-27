//,temp,CertificateLoginModule.java,101,123,temp,PropertiesLoginModule.java,105,132
//,3
public class xxx {
    @Override
    public boolean commit() throws LoginException {
        if (debug) {
            LOG.debug("commit");
        }

        if (!succeeded) {
            clear();
            return false;
        }

        principals.add(new UserPrincipal(username));

        for (String group : getUserGroups(username)) {
             principals.add(new GroupPrincipal(group));
        }

        subject.getPrincipals().addAll(principals);

        username = null;
        commitSucceeded = true;
        return true;
    }

};