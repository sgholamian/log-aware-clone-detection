//,temp,VolumeOrchestrator.java,1529,1537,temp,VpcManagerImpl.java,1772,1785
//,3
public class xxx {
    private void cleanupVolumeDuringSnapshotFailure(Long volumeId, Long snapshotId) {
        _snapshotSrv.cleanupVolumeDuringSnapshotFailure(volumeId, snapshotId);
        VolumeVO volume = _volsDao.findById(volumeId);
        if (volume.getState() == Volume.State.Snapshotting) {
            s_logger.debug("change volume state back to Ready: " + volume.getId());
            volume.setState(Volume.State.Ready);
            _volsDao.update(volume.getId(), volume);
        }
    }

};