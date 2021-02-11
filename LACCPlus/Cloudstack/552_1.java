//,temp,VmwareStorageProcessor.java,3442,3496,temp,VmwareStorageProcessor.java,923,962
//,3
public class xxx {
    @Override
    public Answer createVolumeFromSnapshot(CopyCommand cmd) {
        DataTO srcData = cmd.getSrcTO();
        SnapshotObjectTO snapshot = (SnapshotObjectTO)srcData;
        DataTO destData = cmd.getDestTO();
        DataStoreTO pool = destData.getDataStore();
        DataStoreTO imageStore = srcData.getDataStore();

        if (!(imageStore instanceof NfsTO)) {
            return new CopyCmdAnswer("unsupported protocol");
        }

        NfsTO nfsImageStore = (NfsTO)imageStore;
        String primaryStorageNameLabel = pool.getUuid();

        String secondaryStorageUrl = nfsImageStore.getUrl();
        String backedUpSnapshotUuid = snapshot.getPath();
        int index = backedUpSnapshotUuid.lastIndexOf(File.separator);
        String backupPath = backedUpSnapshotUuid.substring(0, index);
        backedUpSnapshotUuid = backedUpSnapshotUuid.substring(index + 1);
        String details;
        String newVolumeName = VmwareHelper.getVCenterSafeUuid();

        VmwareContext context = hostService.getServiceContext(cmd);
        try {
            VmwareHypervisorHost hyperHost = hostService.getHyperHost(context, cmd);
            ManagedObjectReference morPrimaryDs = HypervisorHostHelper.findDatastoreWithBackwardsCompatibility(hyperHost, primaryStorageNameLabel);
            if (morPrimaryDs == null) {
                String msg = "Unable to find datastore: " + primaryStorageNameLabel;
                s_logger.error(msg);
                throw new Exception(msg);
            }

            // strip off the extension since restoreVolumeFromSecStorage internally will append suffix there.
            if (backedUpSnapshotUuid.endsWith(".ova")){
                backedUpSnapshotUuid = backedUpSnapshotUuid.replace(".ova", "");
            } else if (backedUpSnapshotUuid.endsWith(".ovf")){
                backedUpSnapshotUuid = backedUpSnapshotUuid.replace(".ovf", "");
            }
            DatastoreMO primaryDsMo = new DatastoreMO(hyperHost.getContext(), morPrimaryDs);
            restoreVolumeFromSecStorage(hyperHost, primaryDsMo, newVolumeName, secondaryStorageUrl, backupPath, backedUpSnapshotUuid, (long)cmd.getWait() * 1000, _nfsVersion);

            VolumeObjectTO newVol = new VolumeObjectTO();
            newVol.setPath(newVolumeName);
            return new CopyCmdAnswer(newVol);
        } catch (Throwable e) {
            if (e instanceof RemoteException) {
                hostService.invalidateServiceContext(context);
            }

            s_logger.error("Unexpecpted exception ", e);
            details = "create volume from snapshot exception: " + VmwareHelper.getExceptionMessage(e);
        }
        return new CopyCmdAnswer(details);
    }

};