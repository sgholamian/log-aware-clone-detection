//,temp,XenServerStorageMotionStrategy.java,386,417,temp,HypervStorageMotionStrategy.java,111,143
//,3
public class xxx {
    private Answer migrateVmWithVolumes(VMInstanceVO vm, VirtualMachineTO to, Host srcHost,
            Host destHost, Map<VolumeInfo, DataStore> volumeToPool) throws AgentUnavailableException {

        // Initiate migration of a virtual machine with it's volumes.
        try {
            List<Pair<VolumeTO, StorageFilerTO>> volumeToFilerto = new ArrayList<Pair<VolumeTO, StorageFilerTO>>();
            for (Map.Entry<VolumeInfo, DataStore> entry : volumeToPool.entrySet()) {
                VolumeInfo volume = entry.getKey();
                VolumeTO volumeTo = new VolumeTO(volume, storagePoolDao.findById(volume.getPoolId()));
                StorageFilerTO filerTo = new StorageFilerTO((StoragePool)entry.getValue());
                volumeToFilerto.add(new Pair<VolumeTO, StorageFilerTO>(volumeTo, filerTo));
            }

            MigrateWithStorageCommand command = new MigrateWithStorageCommand(to, volumeToFilerto, destHost.getPrivateIpAddress());
            MigrateWithStorageAnswer answer = (MigrateWithStorageAnswer) agentMgr.send(srcHost.getId(), command);
            if (answer == null) {
                s_logger.error("Migration with storage of vm " + vm + " failed.");
                throw new CloudRuntimeException("Error while migrating the vm " + vm + " to host " + destHost);
            } else if (!answer.getResult()) {
                s_logger.error("Migration with storage of vm " + vm+ " failed. Details: " + answer.getDetails());
                throw new CloudRuntimeException("Error while migrating the vm " + vm + " to host " + destHost +
                        ". " + answer.getDetails());
            } else {
                // Update the volume details after migration.
                updateVolumePathsAfterMigration(volumeToPool, answer.getVolumeTos());
            }

            return answer;
        } catch (OperationTimedoutException e) {
            s_logger.error("Error while migrating vm " + vm + " to host " + destHost, e);
            throw new AgentUnavailableException("Operation timed out on storage motion for " + vm, destHost.getId());
        }
    }

};