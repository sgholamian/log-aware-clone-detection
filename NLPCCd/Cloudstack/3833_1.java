//,temp,sample_5466.java,2,14,temp,sample_5467.java,2,14
//,2
public class xxx {
public boolean start() {
if (_api == null) {
return true;
}
_dbSyncTimer = new Timer("DBSyncTimer");
try {
_dbSyncTimer.schedule(new DBSyncTask(), 0, _dbSyncInterval);
} catch (Exception ex) {


log.info("unable to start db sync timer");
}
}

};