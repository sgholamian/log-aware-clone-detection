//,temp,VmwareStorageManagerImpl.java,515,553,temp,VmwareStorageManagerImpl.java,445,471
//,3
public class xxx {
    @Override
    public Answer execute(VmwareHostService hostService, CreateVolumeFromSnapshotCommand cmd) {

        String primaryStorageNameLabel = cmd.getPrimaryStoragePoolNameLabel();
        Long accountId = cmd.getAccountId();
        Long volumeId = cmd.getVolumeId();
        String secondaryStorageUrl = cmd.getSecondaryStorageUrl();
        String backedUpSnapshotUuid = cmd.getSnapshotUuid();

        String details = null;
        boolean success = false;
        String newVolumeName = UUID.randomUUID().toString().replaceAll("-", "");

        VmwareContext context = hostService.getServiceContext(cmd);
        try {
            VmwareHypervisorHost hyperHost = hostService.getHyperHost(context, cmd);
            ManagedObjectReference morPrimaryDs = HypervisorHostHelper.findDatastoreWithBackwardsCompatibility(hyperHost, primaryStorageNameLabel);
            if (morPrimaryDs == null) {
                String msg = "Unable to find datastore: " + primaryStorageNameLabel;
                s_logger.error(msg);
                throw new Exception(msg);
            }

            DatastoreMO primaryDsMo = new DatastoreMO(hyperHost.getContext(), morPrimaryDs);
            details = createVolumeFromSnapshot(hyperHost, primaryDsMo, newVolumeName, accountId, volumeId, secondaryStorageUrl, backedUpSnapshotUuid, cmd.getNfsVersion());
            if (details == null) {
                success = true;
            }
        } catch (Throwable e) {
            if (e instanceof RemoteException) {
                hostService.invalidateServiceContext(context);
            }

            s_logger.error("Unexpecpted exception ", e);
            details = "CreateVolumeFromSnapshotCommand exception: " + StringUtils.getExceptionStackInfo(e);
        }

        return new CreateVolumeFromSnapshotAnswer(cmd, success, details, newVolumeName);
    }

};