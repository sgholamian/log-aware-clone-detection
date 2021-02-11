//,temp,VolumeServiceImpl.java,1590,1630,temp,VolumeServiceImpl.java,1529,1553
//,3
public class xxx {
    @Override
    public AsyncCallFuture<CommandResult> migrateVolumes(Map<VolumeInfo, DataStore> volumeMap, VirtualMachineTO vmTo, Host srcHost, Host destHost) {
        AsyncCallFuture<CommandResult> future = new AsyncCallFuture<CommandResult>();
        CommandResult res = new CommandResult();
        try {
            // Check to make sure there are no snapshot operations on a volume
            // and
            // put it in the migrating state.
            List<VolumeInfo> volumesMigrating = new ArrayList<VolumeInfo>();
            for (Map.Entry<VolumeInfo, DataStore> entry : volumeMap.entrySet()) {
                VolumeInfo volume = entry.getKey();
                if (!snapshotMgr.canOperateOnVolume(volume)) {
                    s_logger.debug("Snapshots are being created on a volume. Volumes cannot be migrated now.");
                    res.setResult("Snapshots are being created on a volume. Volumes cannot be migrated now.");
                    future.complete(res);

                    // All the volumes that are already in migrating state need
                    // to be put back in ready state.
                    for (VolumeInfo volumeMigrating : volumesMigrating) {
                        volumeMigrating.processEvent(Event.OperationFailed);
                    }
                    return future;
                } else {
                    volume.processEvent(Event.MigrationRequested);
                    volumesMigrating.add(volume);
                }
            }

            MigrateVmWithVolumesContext<CommandResult> context = new MigrateVmWithVolumesContext<CommandResult>(null, future, volumeMap);
            AsyncCallbackDispatcher<VolumeServiceImpl, CopyCommandResult> caller = AsyncCallbackDispatcher.create(this);
            caller.setCallback(caller.getTarget().migrateVmWithVolumesCallBack(null, null)).setContext(context);
            motionSrv.copyAsync(volumeMap, vmTo, srcHost, destHost, caller);

        } catch (Exception e) {
            s_logger.debug("Failed to copy volume", e);
            res.setResult(e.toString());
            future.complete(res);
        }

        return future;
    }

};