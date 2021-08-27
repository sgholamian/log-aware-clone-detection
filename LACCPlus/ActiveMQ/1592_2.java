//,temp,CertificateLoginModule.java,101,123,temp,PropertiesLoginModule.java,105,132
//,3
public class xxx {
    @Override
    public boolean commit() throws LoginException {
        if (!succeeded) {
            clear();
            if (debug) {
                LOG.debug("commit, result: false");
            }
            return false;
        }

        principals.add(new UserPrincipal(user));

        Set<String> matchedGroups = groups.get(user);
        if (matchedGroups != null) {
            for (String entry : matchedGroups) {
                principals.add(new GroupPrincipal(entry));
            }
        }

        subject.getPrincipals().addAll(principals);

        if (debug) {
            LOG.debug("commit, result: true");
        }

        commitSucceeded = true;
        return true;
    }

};