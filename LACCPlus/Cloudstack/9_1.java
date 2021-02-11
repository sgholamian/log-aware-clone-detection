//,temp,Ovm3StoragePool.java,305,319,temp,OvmResourceBase.java,1099,1107
//,3
public class xxx {
    public Answer execute(DeleteStoragePoolCommand cmd) {
        try {
            Pool pool = new Pool(c);
            pool.leaveServerPool(cmd.getPool().getUuid());
            /* also connect to the master and update the pool list ? */
        } catch (Ovm3ResourceException e) {
            LOGGER.debug(
                    "Delete storage pool on host "
                            + config.getAgentHostname()
                            + " failed, however, we leave to user for cleanup and tell managment server it succeeded",
                    e);
        }

        return new Answer(cmd);
    }

};