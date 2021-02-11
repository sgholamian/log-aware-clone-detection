//,temp,AncientDataMotionStrategy.java,480,515,temp,AncientDataMotionStrategy.java,257,301
//,3
public class xxx {
    @DB
    protected Answer createTemplateFromSnapshot(DataObject srcData, DataObject destData) {

        String value = configDao.getValue(Config.CreatePrivateTemplateFromSnapshotWait.toString());
        int _createprivatetemplatefromsnapshotwait = NumbersUtil.parseInt(value, Integer.parseInt(Config.CreatePrivateTemplateFromSnapshotWait.getDefaultValue()));

        boolean needCache = false;
        if (needCacheStorage(srcData, destData)) {
            needCache = true;
            SnapshotInfo snapshot = (SnapshotInfo) srcData;
            srcData = cacheSnapshotChain(snapshot, snapshot.getDataStore().getScope());
        }

        EndPoint ep = null;
        if (srcData.getDataStore().getRole() == DataStoreRole.Primary) {
            ep = selector.select(destData);
        } else {
            ep = selector.select(srcData, destData);
        }

        CopyCommand cmd = new CopyCommand(srcData.getTO(), addFullCloneFlagOnVMwareDest(destData.getTO()), _createprivatetemplatefromsnapshotwait, VirtualMachineManager.ExecuteInSequence.value());
        Answer answer = null;
        if (ep == null) {
            String errMsg = "No remote endpoint to send command, check if host or ssvm is down?";
            s_logger.error(errMsg);
            answer = new Answer(cmd, false, errMsg);
        } else {
            answer = ep.sendMessage(cmd);
        }

        // clean up snapshot copied to staging
        if (needCache && srcData != null) {
            cacheMgr.releaseCacheObject(srcData);  // reduce ref count, but keep it there on cache which is converted from previous secondary storage
        }
        return answer;
    }

};