//,temp,sample_4965.java,2,16,temp,sample_4964.java,2,13
//,3
public class xxx {
private void acquire(Connection dbConn, Statement stmt, List<LockInfo> locksBeingChecked) throws SQLException, NoSuchLockException, MetaException {
if(locksBeingChecked == null || locksBeingChecked.isEmpty()) {
return;
}
long txnId = locksBeingChecked.get(0).txnId;
long extLockId = locksBeingChecked.get(0).extLockId;
long now = getDbTime(dbConn);
String s = "update HIVE_LOCKS set hl_lock_state = '" + LOCK_ACQUIRED + "', " + "hl_last_heartbeat = " + (isValidTxn(txnId) ? 0 : now) + ", hl_acquired_at = " + now + ",HL_BLOCKEDBY_EXT_ID=NULL,HL_BLOCKEDBY_INT_ID=null" + " where hl_lock_ext_id = " +  extLockId;
int rc = stmt.executeUpdate(s);
if (rc < locksBeingChecked.size()) {


log.info("going to rollback acquire connection dbconn statement stmt list lockinfo locksbeingchecked");
}
}

};