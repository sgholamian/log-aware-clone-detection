//,temp,VolumeOrchestrator.java,1508,1527,temp,VolumeOrchestrator.java,1490,1506
//,3
public class xxx {
    private void cleanupVolumeDuringMigrationFailure(Long volumeId, Long destPoolId) {
        StoragePool destPool = (StoragePool)dataStoreMgr.getDataStore(destPoolId, DataStoreRole.Primary);
        if (destPool == null) {
            return;
        }

        VolumeVO volume = _volsDao.findById(volumeId);
        if (volume.getState() == Volume.State.Migrating) {
            VolumeVO duplicateVol = _volsDao.findByPoolIdName(destPoolId, volume.getName());
            if (duplicateVol != null) {
                s_logger.debug("Remove volume " + duplicateVol.getId() + " on storage pool " + destPoolId);
                _volsDao.remove(duplicateVol.getId());
            }

            s_logger.debug("change volume state to ready from migrating in case migration failure for vol: " + volumeId);
            volume.setState(Volume.State.Ready);
            _volsDao.update(volumeId, volume);
        }

    }

};