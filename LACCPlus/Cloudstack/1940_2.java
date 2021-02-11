//,temp,AncientDataMotionStrategy.java,480,515,temp,AncientDataMotionStrategy.java,257,301
//,3
public class xxx {
    protected Answer copyVolumeFromSnapshot(DataObject snapObj, DataObject volObj) {
        SnapshotInfo snapshot = (SnapshotInfo)snapObj;
        StoragePool pool = (StoragePool)volObj.getDataStore();

        String basicErrMsg = "Failed to create volume from " + snapshot.getName() + " on pool " + pool;
        DataStore store = snapObj.getDataStore();
        DataStoreTO storTO = store.getTO();
        DataObject srcData = snapObj;
        try {
            if (!(storTO instanceof NfsTO)) {
                // cache snapshot to zone-wide staging store for the volume to be created
                srcData = cacheSnapshotChain(snapshot, new ZoneScope(pool.getDataCenterId()));
            }

            String value = configDao.getValue(Config.CreateVolumeFromSnapshotWait.toString());
            int _createVolumeFromSnapshotWait = NumbersUtil.parseInt(value, Integer.parseInt(Config.CreateVolumeFromSnapshotWait.getDefaultValue()));

            EndPoint ep = null;
            if (srcData.getDataStore().getRole() == DataStoreRole.Primary) {
                ep = selector.select(volObj);
            } else {
                ep = selector.select(srcData, volObj);
            }

            CopyCommand cmd = new CopyCommand(srcData.getTO(), addFullCloneFlagOnVMwareDest(volObj.getTO()), _createVolumeFromSnapshotWait, VirtualMachineManager.ExecuteInSequence.value());
            Answer answer = null;
            if (ep == null) {
                String errMsg = "No remote endpoint to send command, check if host or ssvm is down?";
                s_logger.error(errMsg);
                answer = new Answer(cmd, false, errMsg);
            } else {
                answer = ep.sendMessage(cmd);
            }

            return answer;
        } catch (Exception e) {
            s_logger.error(basicErrMsg, e);
            throw new CloudRuntimeException(basicErrMsg);
        } finally {
            if (!(storTO instanceof NfsTO)) {
                // still keep snapshot on cache which may be migrated from previous secondary storage
                releaseSnapshotCacheChain((SnapshotInfo)srcData);
            }
        }
    }

};