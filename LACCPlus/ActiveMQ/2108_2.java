//,temp,CertificateLoginModule.java,149,161,temp,PropertiesLoginModule.java,152,163
//,3
public class xxx {
    @Override
    public boolean logout() throws LoginException {
        subject.getPrincipals().removeAll(principals);
        clear();
        if (debug) {
            LOG.debug("logout");
        }

        succeeded = false;
        commitSucceeded = false;
        return true;
    }

};