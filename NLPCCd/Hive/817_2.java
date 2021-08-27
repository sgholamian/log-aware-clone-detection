//,temp,sample_4899.java,2,16,temp,sample_4900.java,2,17
//,3
public class xxx {
public void dummy_method(){
s = "delete from TXN_COMPONENTS where tc_txnid = " + txnid;
modCount = stmt.executeUpdate(s);
s = "delete from HIVE_LOCKS where hl_txnid = " + txnid;
modCount = stmt.executeUpdate(s);
s = "delete from TXNS where txn_id = " + txnid;
modCount = stmt.executeUpdate(s);
dbConn.commit();
s = "select ctc_database, ctc_table, ctc_id, ctc_timestamp from COMPLETED_TXN_COMPONENTS where ctc_txnid = " + txnid;
rs = stmt.executeQuery(s);
if (rs.next()) {


log.info("going to register table modification in invalidation cache");
}
}

};