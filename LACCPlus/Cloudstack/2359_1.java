//,temp,CloudianConnectorImpl.java,295,305,temp,CloudianConnectorImpl.java,281,291
//,3
public class xxx {
            @Override
            public void onPublishMessage(String senderAddress, String subject, Object args) {
                try {
                    final DomainVO domain = (DomainVO) args;
                    if (!removeGroup(domain)) {
                        LOG.warn(String.format("Failed to remove group in Cloudian while removing CloudStack domain=%s id=%s", domain.getPath(), domain.getId()));
                    }
                } catch (final Exception e) {
                    LOG.error("Caught exception while removing domain/group in Cloudian: ", e);
                }
            }

};