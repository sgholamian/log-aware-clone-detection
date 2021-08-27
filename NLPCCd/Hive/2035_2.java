//,temp,sample_3640.java,2,16,temp,sample_3641.java,2,16
//,2
public class xxx {
public void dummy_method(){
Collections.sort(txnids);
List<String> queries = new ArrayList<>();
StringBuilder prefix = new StringBuilder();
StringBuilder suffix = new StringBuilder();
prefix.append("delete from TXNS where ");
suffix.append("");
TxnUtils.buildQueryWithINClause(conf, queries, prefix, suffix, txnids, "txn_id", false, false);
for (String query : queries) {
int rc = stmt.executeUpdate(query);
}


log.info("going to commit");
}

};