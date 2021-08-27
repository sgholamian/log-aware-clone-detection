//,temp,sample_4874.java,2,16,temp,sample_4878.java,2,16
//,2
public class xxx {
public void dummy_method(){
if (!rs.next()) {
throw new MetaException("Transaction tables not properly " + "initialized, no record found in next_txn_id");
}
long hwm = rs.getLong(1);
if (rs.wasNull()) {
throw new MetaException("Transaction tables not properly " + "initialized, null record found in next_txn_id");
}
close(rs);
List<TxnInfo> txnInfos = new ArrayList<>();
s = "select txn_id, txn_state, txn_user, txn_host, txn_started, txn_last_heartbeat from " + "TXNS where txn_id <= " + hwm;


log.info("going to execute query");
}

};