//,temp,Ovm3StoragePool.java,114,132,temp,Ovm3StoragePool.java,95,105
//,3
public class xxx {
    private void takeOwnership(Pool pool) throws ConfigurationException {
        try {
            LOGGER.debug("Take ownership of host " + config.getAgentHostname());
            pool.takeOwnership(config.getAgentOwnedByUuid(), "");
        } catch (Ovm3ResourceException e) {
            String msg = "Failed to take ownership of host "
                    + config.getAgentHostname();
            LOGGER.error(msg);
            throw new ConfigurationException(msg);
        }
    }

};