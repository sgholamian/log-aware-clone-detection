//,temp,sample_3631.java,2,16,temp,sample_3630.java,2,17
//,3
public class xxx {
public void dummy_method(){
int paramCount = 1;
pStmt.setString(paramCount++, info.dbname);
pStmt.setString(paramCount++, info.tableName);
if (info.partName != null) {
pStmt.setString(paramCount++, info.partName);
}
if(info.highestTxnId != 0) {
pStmt.setLong(paramCount++, info.highestTxnId);
}
if (pStmt.executeUpdate() < 1) {


log.info("expected to remove at least one row from completed txn components when marking compaction entry as clean");
}
}

};