//,temp,VmwareStorageManagerImpl.java,515,553,temp,VmwareStorageManagerImpl.java,445,471
//,3
public class xxx {
    @Override
    public Answer execute(VmwareHostService hostService, CreatePrivateTemplateFromSnapshotCommand cmd) {
        Long accountId = cmd.getAccountId();
        Long volumeId = cmd.getVolumeId();
        String secondaryStorageUrl = cmd.getSecondaryStorageUrl();
        String backedUpSnapshotUuid = cmd.getSnapshotUuid();
        Long newTemplateId = cmd.getNewTemplateId();
        String details;
        String uniqeName = UUID.randomUUID().toString();

        VmwareContext context = hostService.getServiceContext(cmd);
        try {
            Ternary<String, Long, Long> result = createTemplateFromSnapshot(accountId, newTemplateId, uniqeName, secondaryStorageUrl, volumeId, backedUpSnapshotUuid,
                    cmd.getNfsVersion());

            return new CreatePrivateTemplateAnswer(cmd, true, null, result.first(), result.third(), result.second(), uniqeName, ImageFormat.OVA);
        } catch (Throwable e) {
            if (e instanceof RemoteException) {
                hostService.invalidateServiceContext(context);
            }

            s_logger.error("Unexpecpted exception ", e);

            details = "CreatePrivateTemplateFromSnapshotCommand exception: " + StringUtils.getExceptionStackInfo(e);
            return new CreatePrivateTemplateAnswer(cmd, false, details);
        }
    }

};