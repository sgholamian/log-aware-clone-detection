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
List<Long> openList = new ArrayList<>();
s = "select txn_id, txn_state from TXNS where txn_id <= " + hwm + " order by txn_id";


log.info("going to execute query");
}

};