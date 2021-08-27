//,temp,HdfsComponent.java,61,70,temp,SftpOperations.java,602,612
//,3
public class xxx {
    public static Configuration getJAASConfiguration() {
        Configuration auth = null;
        try {
            auth = Configuration.getConfiguration();
            LOG.trace("Existing JAAS Configuration {}", auth);
        } catch (SecurityException e) {
            LOG.trace("Cannot load existing JAAS configuration", e);
        }
        return auth;
    }

};