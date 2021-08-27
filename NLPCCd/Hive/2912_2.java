//,temp,sample_3604.java,2,16,temp,sample_3605.java,2,16
//,3
public class xxx {
public void dummy_method(){
s = "select tc_database, tc_table, tc_partition " + "from TXNS, TXN_COMPONENTS " + "where txn_id = tc_txnid and txn_state = '" + TXN_ABORTED + "' " + "group by tc_database, tc_table, tc_partition " + "having count(*) > " + maxAborted;
rs = stmt.executeQuery(s);
while (rs.next()) {
CompactionInfo info = new CompactionInfo();
info.dbname = rs.getString(1);
info.tableName = rs.getString(2);
info.partName = rs.getString(3);
info.tooManyAborts = true;
response.add(info);
}


log.info("going to rollback");
}

};