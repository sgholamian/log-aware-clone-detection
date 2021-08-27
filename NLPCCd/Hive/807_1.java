//,temp,sample_4885.java,2,16,temp,sample_4884.java,2,17
//,3
public class xxx {
public void dummy_method(){
List<Long> txnIds = new ArrayList<>(numTxns);
List<String> rows = new ArrayList<>();
for (long i = first; i < first + numTxns; i++) {
txnIds.add(i);
rows.add(i + "," + quoteChar(TXN_OPEN) + "," + now + "," + now + "," + quoteString(rqst.getUser()) + "," + quoteString(rqst.getHostname()));
}
List<String> queries = sqlGenerator.createInsertValuesStmt( "TXNS (txn_id, txn_state, txn_started, txn_last_heartbeat, txn_user, txn_host)", rows);
for (String q : queries) {
stmt.execute(q);
}


log.info("going to commit");
}

};