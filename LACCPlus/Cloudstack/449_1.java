//,temp,LibvirtStorageAdaptor.java,234,264,temp,LibvirtStorageAdaptor.java,203,232
//,3
public class xxx {
    private StoragePool createCLVMStoragePool(Connect conn, String uuid, String host, String path) {

        String volgroupPath = "/dev/" + path;
        String volgroupName = path;
        volgroupName = volgroupName.replaceFirst("/", "");

        LibvirtStoragePoolDef spd = new LibvirtStoragePoolDef(PoolType.LOGICAL, volgroupName, uuid, host, volgroupPath, volgroupPath);
        StoragePool sp = null;
        try {
            s_logger.debug(spd.toString());
            sp = conn.storagePoolCreateXML(spd.toString(), 0);
            return sp;
        } catch (LibvirtException e) {
            s_logger.error(e.toString());
            if (sp != null) {
                try {
                    if (sp.isPersistent() == 1) {
                        sp.destroy();
                        sp.undefine();
                    } else {
                        sp.destroy();
                    }
                    sp.free();
                } catch (LibvirtException l) {
                    s_logger.debug("Failed to define clvm storage pool with: " + l.toString());
                }
            }
            return null;
        }

    }

};