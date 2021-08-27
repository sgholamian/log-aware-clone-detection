//,temp,sample_4908.java,2,16,temp,sample_4909.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (isValidTxn(txnid)) {
lockHandle = lockTransactionRecord(stmt, txnid, TXN_OPEN);
if(lockHandle == null) {
ensureValidTxn(dbConn, txnid, stmt);
shouldNeverHappen(txnid);
}
}
String s = sqlGenerator.addForUpdateClause("select nl_next from NEXT_LOCK_ID");
rs = stmt.executeQuery(s);
if (!rs.next()) {


log.info("going to rollback");
}
}

};