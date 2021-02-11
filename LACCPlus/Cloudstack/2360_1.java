//,temp,CloudianConnectorImpl.java,157,172,temp,CloudianConnectorImpl.java,100,119
//,3
public class xxx {
    private boolean removeUserAccount(final Account account) {
        if (account == null || !isEnabled()) {
            return false;
        }
        final CloudianClient client = getClient();
        final Domain domain = domainDao.findById(account.getDomainId());
        for (int retry = 0; retry < 3; retry++) {
            if (client.removeUser(account.getUuid(), domain.getUuid())) {
                return true;
            } else {
                LOG.warn("Failed to remove Cloudian user id=" + account.getUuid() + " in group id=" + domain.getUuid() + ", retrying count=" + retry+1);
            }
        }
        LOG.warn("Failed to remove Cloudian user id=" + account.getUuid() + " in group id=" + domain.getUuid() + ", please remove manually");
        return false;
    }

};