//,temp,VmwareStorageMotionStrategy.java,317,348,temp,VmwareStorageMotionStrategy.java,279,315
//,3
public class xxx {
    private Answer migrateVmWithVolumesAcrossCluster(VMInstanceVO vm, VirtualMachineTO to, Host srcHost, Host destHost, Map<VolumeInfo, DataStore> volumeToPool)
            throws AgentUnavailableException {

        // Initiate migration of a virtual machine with it's volumes.
        try {
            List<Pair<VolumeTO, StorageFilerTO>> volumeToFilerto = new ArrayList<Pair<VolumeTO, StorageFilerTO>>();
            for (Map.Entry<VolumeInfo, DataStore> entry : volumeToPool.entrySet()) {
                VolumeInfo volume = entry.getKey();
                VolumeTO volumeTo = new VolumeTO(volume, storagePoolDao.findById(volume.getPoolId()));
                StorageFilerTO filerTo = new StorageFilerTO((StoragePool) entry.getValue());
                volumeToFilerto.add(new Pair<VolumeTO, StorageFilerTO>(volumeTo, filerTo));
            }

            // Migration across cluster needs to be done in three phases.
            // 1. Send a migrate command to source resource to initiate migration
            //      Run validations against target!!
            // 2. Complete the process. Update the volume details.
            MigrateWithStorageCommand migrateWithStorageCmd = new MigrateWithStorageCommand(to, volumeToFilerto, destHost.getGuid());
            MigrateWithStorageAnswer migrateWithStorageAnswer = (MigrateWithStorageAnswer) agentMgr.send(srcHost.getId(), migrateWithStorageCmd);
            if (migrateWithStorageAnswer == null) {
                s_logger.error("Migration with storage of vm " + vm + " to host " + destHost + " failed.");
                throw new CloudRuntimeException("Error while migrating the vm " + vm + " to host " + destHost);
            } else if (!migrateWithStorageAnswer.getResult()) {
                s_logger.error("Migration with storage of vm " + vm + " failed. Details: " + migrateWithStorageAnswer.getDetails());
                throw new CloudRuntimeException("Error while migrating the vm " + vm + " to host " + destHost + ". " + migrateWithStorageAnswer.getDetails());
            } else {
                // Update the volume details after migration.
                updateVolumesAfterMigration(volumeToPool, migrateWithStorageAnswer.getVolumeTos());
            }
            s_logger.debug("Storage migration of VM " + vm.getInstanceName() + " completed successfully. Migrated to host " + destHost.getName());

            return migrateWithStorageAnswer;
        } catch (OperationTimedoutException e) {
            s_logger.error("Error while migrating vm " + vm + " to host " + destHost, e);
            throw new AgentUnavailableException("Operation timed out on storage motion for " + vm, destHost.getId());
        }
    }

};