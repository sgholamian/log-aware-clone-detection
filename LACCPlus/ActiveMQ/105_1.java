//,temp,LdapNetworkConnector.java,443,453,temp,LdapNetworkConnector.java,415,423
//,3
public class xxx {
    @Override
    public void objectChanged(NamingEvent event) {
        LOG.debug("entry changed");
        try {
            SearchResult result = (SearchResult) event.getNewBinding();
            removeConnector(result);
            addConnector(result);
        } catch (Exception err) {
            LOG.error("ERR: caught unexpected exception", err);
        }
    }

};