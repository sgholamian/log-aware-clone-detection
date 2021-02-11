//,temp,Ovm3StoragePool.java,305,319,temp,OvmResourceBase.java,1099,1107
//,3
public class xxx {
    protected Answer execute(DeleteStoragePoolCommand cmd) {
        try {
            OvmStoragePool.delete(_conn, cmd.getPool().getUuid());
        } catch (Exception e) {
            s_logger.debug("Delete storage pool on host " + _ip + " failed, however, we leave to user for cleanup and tell managment server it succeeded", e);
        }

        return new Answer(cmd);
    }

};