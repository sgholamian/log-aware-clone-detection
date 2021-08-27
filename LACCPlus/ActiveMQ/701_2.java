//,temp,GuestLoginModule.java,140,151,temp,GuestLoginModule.java,107,120
//,3
public class xxx {
    @Override
    public boolean commit() throws LoginException {
        if (debug) {
            LOG.debug("commit");
        }

        if (!succeeded) {
            return false;
        }

        subject.getPrincipals().addAll(principals);
        commitSucceeded = true;
        return true;
    }

};