//,temp,OvmResourceBase.java,424,431,temp,OvmResourceBase.java,414,422
//,3
public class xxx {
    protected void createOCFS2Sr(StorageFilerTO pool) throws XmlRpcException {
        OvmStoragePool.Details d = new OvmStoragePool.Details();
        d.path = pool.getPath();
        d.type = OvmStoragePool.OCFS2;
        d.uuid = pool.getUuid();
        OvmStoragePool.create(_conn, d);
        s_logger.debug(String.format("Created SR (mount point:%1$s)", d.path));
    }

};