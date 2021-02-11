//,temp,UserVmManagerImpl.java,6642,6652,temp,UserVmManagerImpl.java,6630,6640
//,3
public class xxx {
    private void detachVolumesFromVm(List<VolumeVO> volumes) {

        for (VolumeVO volume : volumes) {

            Volume detachResult = _volumeService.detachVolumeViaDestroyVM(volume.getInstanceId(), volume.getId());

            if (detachResult == null) {
                s_logger.error("DestroyVM remove volume - failed to detach and delete volume " + volume.getInstanceId() + " from instance " + volume.getId());
            }
        }
    }

};