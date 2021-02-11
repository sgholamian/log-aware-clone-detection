//,temp,CloudianConnectorImpl.java,295,305,temp,CloudianConnectorImpl.java,281,291
//,3
public class xxx {
            @Override
            public void onPublishMessage(String senderAddress, String subject, Object args) {
                try {
                    final Domain domain = domainDao.findById((Long) args);
                    if (!addGroup(domain)) {
                        LOG.warn(String.format("Failed to add group in Cloudian while adding CloudStack domain=%s id=%s", domain.getPath(), domain.getId()));
                    }
                } catch (final Exception e) {
                    LOG.error("Caught exception adding domain/group in Cloudian: ", e);
                }
            }

};