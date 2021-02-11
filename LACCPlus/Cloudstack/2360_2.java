//,temp,CloudianConnectorImpl.java,157,172,temp,CloudianConnectorImpl.java,100,119
//,3
public class xxx {
    private boolean removeGroup(final Domain domain) {
        if (domain == null || !isEnabled()) {
            return false;
        }
        final CloudianClient client = getClient();
        for (final CloudianUser user: client.listUsers(domain.getUuid())) {
            if (client.removeUser(user.getUserId(), domain.getUuid())) {
                LOG.error(String.format("Failed to remove Cloudian user id=%s, while removing Cloudian group id=%s", user.getUserId(), domain.getUuid()));
            }
        }
        for (int retry = 0; retry < 3; retry++) {
            if (client.removeGroup(domain.getUuid())) {
                return true;
            } else {
                LOG.warn("Failed to remove Cloudian group id=" + domain.getUuid() + ", retrying count=" + retry+1);
            }
        }
        LOG.warn("Failed to remove Cloudian group id=" + domain.getUuid() + ", please remove manually");
        return false;
    }

};