//,temp,KVMStoragePoolManager.java,192,239,temp,KVMStoragePoolManager.java,134,166
//,3
public class xxx {
    public boolean disconnectPhysicalDisksViaVmSpec(VirtualMachineTO vmSpec) {
        if (vmSpec == null) {
            /* CloudStack often tries to stop VMs that shouldn't be running, to ensure a known state,
               for example if we lose communication with the agent and the VM is brought up elsewhere.
               We may not know about these yet. This might mean that we can't use the vmspec map, because
               when we restart the agent we lose all of the info about running VMs. */

            s_logger.debug("disconnectPhysicalDiskViaVmSpec: Attempted to stop a VM that is not yet in our hash map");

            return true;
        }

        boolean result = true;

        final String vmName = vmSpec.getName();

        List<DiskTO> disks = Arrays.asList(vmSpec.getDisks());

        for (DiskTO disk : disks) {
            if (disk.getType() != Volume.Type.ISO) {
                s_logger.debug("Disconnecting disk " + disk.getPath());

                VolumeObjectTO vol = (VolumeObjectTO)disk.getData();
                PrimaryDataStoreTO store = (PrimaryDataStoreTO)vol.getDataStore();

                KVMStoragePool pool = getStoragePool(store.getPoolType(), store.getUuid());

                if (pool == null) {
                    s_logger.error("Pool " + store.getUuid() + " of type " + store.getPoolType() + " was not found, skipping disconnect logic");
                    continue;
                }

                StorageAdaptor adaptor = getStorageAdaptor(pool.getType());

                // if a disk fails to disconnect, still try to disconnect remaining

                boolean subResult = adaptor.disconnectPhysicalDisk(vol.getPath(), pool);

                if (!subResult) {
                    s_logger.error("Failed to disconnect disks via vm spec for vm: " + vmName + " volume:" + vol.toString());

                    result = false;
                }
            }
        }

        return result;
    }

};