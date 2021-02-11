//,temp,VolumeOrchestrator.java,1508,1527,temp,VolumeOrchestrator.java,1490,1506
//,3
public class xxx {
    private void cleanupVolumeDuringAttachFailure(Long volumeId, Long vmId) {
        VolumeVO volume = _volsDao.findById(volumeId);
        if (volume == null) {
            return;
        }

        if (volume.getState().equals(Volume.State.Creating)) {
            s_logger.debug("Remove volume: " + volume.getId() + ", as it's leftover from last mgt server stop");
            _volsDao.remove(volume.getId());
        }

        if (volume.getState().equals(Volume.State.Attaching)) {
            s_logger.warn("Vol: " + volume.getName() + " failed to attach to VM: " + _userVmDao.findById(vmId).getHostName() + " on last mgt server stop, changing state back to Ready");
            volume.setState(Volume.State.Ready);
            _volsDao.update(volumeId, volume);
        }
    }

};