//,temp,sample_3652.java,2,17,temp,sample_3632.java,2,17
//,3
public class xxx {
public void dummy_method(){
suffix.append(" and tc_database = ?");
suffix.append(" and tc_table = ?");
if (info.partName != null) {
suffix.append(" and tc_partition = ?");
}
List<Integer> counts = TxnUtils .buildQueryWithINClauseStrings(conf, queries, prefix, suffix, questions, "tc_txnid", true, false);
int totalCount = 0;
for (int i = 0; i < queries.size(); i++) {
String query = queries.get(i);
int insertCount = counts.get(i);


log.info("going to execute update");
}
}

};