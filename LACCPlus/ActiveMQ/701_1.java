//,temp,GuestLoginModule.java,140,151,temp,GuestLoginModule.java,107,120
//,3
public class xxx {
    @Override
    public boolean logout() throws LoginException {
        subject.getPrincipals().removeAll(principals);

        if (debug) {
            LOG.debug("logout");
        }

        succeeded = false;
        commitSucceeded = false;
        return true;
    }

};