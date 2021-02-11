//,temp,VmwareStorageMotionStrategy.java,254,277,temp,HypervStorageMotionStrategy.java,89,109
//,3
public class xxx {
    @Override
    public void copyAsync(Map<VolumeInfo, DataStore> volumeMap, VirtualMachineTO vmTo, Host srcHost, Host destHost, AsyncCompletionCallback<CopyCommandResult> callback) {
        Answer answer = null;
        String errMsg = null;
        try {
            VMInstanceVO instance = instanceDao.findById(vmTo.getId());
            if (instance != null) {
                if (srcHost.getClusterId().equals(destHost.getClusterId())) {
                    answer = migrateVmWithVolumesWithinCluster(instance, vmTo, srcHost, destHost, volumeMap);
                } else {
                    answer = migrateVmWithVolumesAcrossCluster(instance, vmTo, srcHost, destHost, volumeMap);
                }
            } else {
                throw new CloudRuntimeException("Unsupported operation requested for moving data.");
            }
        } catch (Exception e) {
            s_logger.error("copy failed", e);
            errMsg = e.toString();
        }

        CopyCommandResult result = new CopyCommandResult(null, answer);
        result.setResult(errMsg);
        callback.complete(result);
    }

};