//,temp,LdapNetworkConnector.java,443,453,temp,LdapNetworkConnector.java,415,423
//,3
public class xxx {
    @Override
    public void objectRemoved(NamingEvent event) {
        LOG.debug("entry removed");
        try {
            removeConnector((SearchResult) event.getOldBinding());
        } catch (Exception err) {
            LOG.error("ERR: caught unexpected exception", err);
        }
    }

};