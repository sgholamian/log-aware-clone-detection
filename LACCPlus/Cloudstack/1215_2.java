//,temp,KVMStoragePoolManager.java,192,239,temp,KVMStoragePoolManager.java,134,166
//,3
public class xxx {
    public boolean connectPhysicalDisksViaVmSpec(VirtualMachineTO vmSpec) {
        boolean result = false;

        final String vmName = vmSpec.getName();

        List<DiskTO> disks = Arrays.asList(vmSpec.getDisks());

        for (DiskTO disk : disks) {
            if (disk.getType() == Volume.Type.ISO) {
                result = true;
                continue;
            }

            VolumeObjectTO vol = (VolumeObjectTO)disk.getData();
            PrimaryDataStoreTO store = (PrimaryDataStoreTO)vol.getDataStore();
            if (!store.isManaged() && VirtualMachine.State.Migrating.equals(vmSpec.getState())) {
                result = true;
                continue;
            }

            KVMStoragePool pool = getStoragePool(store.getPoolType(), store.getUuid());
            StorageAdaptor adaptor = getStorageAdaptor(pool.getType());

            result = adaptor.connectPhysicalDisk(vol.getPath(), pool, disk.getDetails());

            if (!result) {
                s_logger.error("Failed to connect disks via vm spec for vm: " + vmName + " volume:" + vol.toString());
                return result;
            }
        }

        return result;
    }

};