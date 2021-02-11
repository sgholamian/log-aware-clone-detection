//,temp,OvmResourceBase.java,424,431,temp,OvmResourceBase.java,414,422
//,3
public class xxx {
    protected void createNfsSr(StorageFilerTO pool) throws XmlRpcException {
        String mountPoint = String.format("%1$s:%2$s", pool.getHost(), pool.getPath());
        OvmStoragePool.Details d = new OvmStoragePool.Details();
        d.path = mountPoint;
        d.type = OvmStoragePool.NFS;
        d.uuid = pool.getUuid();
        OvmStoragePool.create(_conn, d);
        s_logger.debug(String.format("Created SR (mount point:%1$s)", mountPoint));
    }

};