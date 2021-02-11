//,temp,LibvirtStorageAdaptor.java,699,728,temp,ManagedNfsStorageAdaptor.java,169,209
//,3
public class xxx {
    private KVMPhysicalDisk createPhysicalDiskByLibVirt(String name, KVMStoragePool pool,
            PhysicalDiskFormat format, Storage.ProvisioningType provisioningType, long size) {
        LibvirtStoragePool libvirtPool = (LibvirtStoragePool) pool;
        StoragePool virtPool = libvirtPool.getPool();
        LibvirtStorageVolumeDef.VolumeFormat libvirtformat = LibvirtStorageVolumeDef.VolumeFormat.getFormat(format);

        String volPath = null;
        String volName = null;
        long volAllocation = 0;
        long volCapacity = 0;

        LibvirtStorageVolumeDef volDef = new LibvirtStorageVolumeDef(name,
                size, libvirtformat, null, null);
        s_logger.debug(volDef.toString());
        try {
            StorageVol vol = virtPool.storageVolCreateXML(volDef.toString(), 0);
            volPath = vol.getPath();
            volName = vol.getName();
            volAllocation = vol.getInfo().allocation;
            volCapacity = vol.getInfo().capacity;
        } catch (LibvirtException e) {
            throw new CloudRuntimeException(e.toString());
        }

        KVMPhysicalDisk disk = new KVMPhysicalDisk(volPath, volName, pool);
        disk.setFormat(format);
        disk.setSize(volAllocation);
        disk.setVirtualSize(volCapacity);
        return disk;
    }

};