//,temp,Ovm3StoragePool.java,114,132,temp,Ovm3StoragePool.java,95,105
//,3
public class xxx {
    private void takeOwnership33x(Pool pool) throws ConfigurationException {
        try {
            LOGGER.debug("Take ownership of host " + config.getAgentHostname());
            String event = "http://localhost:10024/event";
            String stats = "http://localhost:10024/stats";
            String mgrCert = "None";
            String signCert = "None";
            pool.takeOwnership33x(config.getAgentOwnedByUuid(),
                    event,
                    stats,
                    mgrCert,
                    signCert);
        } catch (Ovm3ResourceException e) {
            String msg = "Failed to take ownership of host "
                    + config.getAgentHostname();
            LOGGER.error(msg);
            throw new ConfigurationException(msg);
        }
    }

};