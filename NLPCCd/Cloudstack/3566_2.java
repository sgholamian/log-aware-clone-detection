//,temp,sample_5471.java,2,10,temp,sample_5472.java,2,11
//,2
public class xxx {
public void syncNetworkDB(short syncMode) throws IOException {
if (_dbSync.syncAll(syncMode) == ServerDBSync.SYNC_STATE_OUT_OF_SYNC) {
if (syncMode == DBSyncGeneric.SYNC_MODE_CHECK) {
} else {


log.info("cloudstack db vnc were out of sync performed re sync operation");
}
}
}

};