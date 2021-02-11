//,temp,CloudianConnectorImpl.java,295,305,temp,CloudianConnectorImpl.java,267,277
//,3
public class xxx {
            @Override
            public void onPublishMessage(String senderAddress, String subject, Object args) {
                try {
                    final Account account = accountDao.findByIdIncludingRemoved((Long) args);
                    if(!removeUserAccount(account))    {
                        LOG.warn(String.format("Failed to remove account to Cloudian while removing CloudStack account=%s, id=%s", account.getAccountName(), account.getId()));
                    }
                } catch (final Exception e) {
                    LOG.error("Caught exception while removing account in Cloudian: ", e);
                }
            }

};