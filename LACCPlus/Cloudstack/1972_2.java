//,temp,LibvirtStorageAdaptor.java,699,728,temp,ManagedNfsStorageAdaptor.java,169,209
//,3
public class xxx {
    @Override
    public KVMPhysicalDisk getPhysicalDisk(String volumeUuid, KVMStoragePool pool) {
        // now create the volume upon the given storage pool in kvm
        Connect conn;
        StoragePool virtPool = null;
        try {
            conn = LibvirtConnection.getConnection();
            virtPool = conn.storagePoolLookupByName("/" + volumeUuid);
        } catch (LibvirtException e1) {
            throw new CloudRuntimeException(e1.toString());
        }

        LibvirtStorageVolumeDef.VolumeFormat libvirtformat = null;
        long volCapacity = 0;
        // check whether the volume is present on the given pool
        StorageVol vol = getVolume(virtPool, volumeUuid);
        try {
            if (vol == null) {

                libvirtformat = LibvirtStorageVolumeDef.VolumeFormat.QCOW2;

                StoragePoolInfo poolinfo = virtPool.getInfo();
                volCapacity = poolinfo.available;

                LibvirtStorageVolumeDef volDef = new LibvirtStorageVolumeDef(volumeUuid, volCapacity, libvirtformat, null, null);
                s_logger.debug(volDef.toString());

                vol = virtPool.storageVolCreateXML(volDef.toString(), 0);

            }
            KVMPhysicalDisk disk = new KVMPhysicalDisk(vol.getPath(), volumeUuid, pool);
            disk.setFormat(PhysicalDiskFormat.QCOW2);
            disk.setSize(vol.getInfo().allocation);
            disk.setVirtualSize(vol.getInfo().capacity);
            return disk;

        } catch (LibvirtException e) {
            throw new CloudRuntimeException(e.toString());
        }

    }

};