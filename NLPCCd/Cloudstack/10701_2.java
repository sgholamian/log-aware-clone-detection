//,temp,sample_7940.java,2,17,temp,sample_6742.java,2,17
//,3
public class xxx {
public void dummy_method(){
TransactionLegacy txn = TransactionLegacy.currentTxn();
PreparedStatement pstmt = null;
String sql = GET_LAST_VM_DISK_STATS;
try {
pstmt = txn.prepareAutoCloseStatement(sql);
ResultSet rs = pstmt.executeQuery();
if (rs.next()) {
return Long.valueOf(rs.getLong(1));
}
} catch (Exception ex) {


log.info("error getting last vm disk stats id");
}
}

};