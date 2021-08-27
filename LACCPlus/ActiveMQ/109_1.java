//,temp,PropertiesLoginModule.java,134,150,temp,GuestLoginModule.java,122,138
//,3
public class xxx {
    @Override
    public boolean abort() throws LoginException {
        if (debug) {
            LOG.debug("abort");
        }
        if (!succeeded) {
            return false;
        } else if (succeeded && commitSucceeded) {
            // we succeeded, but another required module failed
            logout();
        } else {
            // our commit failed
            clear();
            succeeded = false;
        }
        return true;
    }

};