//,temp,sample_4295.java,2,6,temp,sample_4975.java,2,7
//,3
public class xxx {
private static void ensureValidTxn(Connection dbConn, long txnid, Statement stmt) throws SQLException, NoSuchTxnException, TxnAbortedException {
String s = "select txn_state from TXNS where txn_id = " + txnid;


log.info("going to execute query");
}

};