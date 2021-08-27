//,temp,sample_4899.java,2,16,temp,sample_4900.java,2,17
//,3
public class xxx {
public void dummy_method(){
String s = "insert into COMPLETED_TXN_COMPONENTS (ctc_txnid, ctc_database, " + "ctc_table, ctc_partition) select tc_txnid, tc_database, tc_table, " + "tc_partition from TXN_COMPONENTS where tc_txnid = " + txnid;
int modCount = 0;
if ((modCount = stmt.executeUpdate(s)) < 1) {
}
s = "delete from TXN_COMPONENTS where tc_txnid = " + txnid;
modCount = stmt.executeUpdate(s);
s = "delete from HIVE_LOCKS where hl_txnid = " + txnid;
modCount = stmt.executeUpdate(s);
s = "delete from TXNS where txn_id = " + txnid;
modCount = stmt.executeUpdate(s);


log.info("going to commit");
}

};