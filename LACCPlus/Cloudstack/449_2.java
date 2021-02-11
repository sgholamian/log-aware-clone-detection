//,temp,LibvirtStorageAdaptor.java,234,264,temp,LibvirtStorageAdaptor.java,203,232
//,3
public class xxx {
    private StoragePool createSharedStoragePool(Connect conn, String uuid, String host, String path) {
        String mountPoint = path;
        if (!_storageLayer.exists(mountPoint)) {
            s_logger.error(mountPoint + " does not exists. Check local.storage.path in agent.properties.");
            return null;
        }
        LibvirtStoragePoolDef spd = new LibvirtStoragePoolDef(PoolType.DIR, uuid, uuid, host, path, path);
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
                    s_logger.debug("Failed to define shared mount point storage pool with: " + l.toString());
                }
            }
            return null;
        }
    }

};