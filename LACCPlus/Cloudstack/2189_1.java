//,temp,UserVmManagerImpl.java,6642,6652,temp,UserVmManagerImpl.java,6630,6640
//,3
public class xxx {
    private void deleteVolumesFromVm(List<VolumeVO> volumes) {

        for (VolumeVO volume : volumes) {

            boolean deleteResult = _volumeService.deleteVolume(volume.getId(), CallContext.current().getCallingAccount());

            if (!deleteResult) {
                s_logger.error("DestroyVM remove volume - failed to delete volume " + volume.getInstanceId() + " from instance " + volume.getId());
            }
        }
    }

};