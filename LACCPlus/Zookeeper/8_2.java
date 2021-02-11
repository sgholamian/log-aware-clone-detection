//,temp,SaslQuorumServerCallbackHandler.java,93,102,temp,SaslServerCallbackHandler.java,90,98
//,3
public class xxx {
    private void handleNameCallback(NameCallback nc) {
        // check to see if this user is in the user password database.
        if (credentials.get(nc.getDefaultName()) == null) {
            LOG.warn("User '" + nc.getDefaultName() + "' not found in list of DIGEST-MD5 authenticateable users.");
            return;
        }
        nc.setName(nc.getDefaultName());
        userName = nc.getDefaultName();
    }

};