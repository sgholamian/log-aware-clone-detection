//,temp,sample_6739.java,2,17,temp,sample_6740.java,2,17
//,2
public class xxx {
public void dummy_method(){
TransactionLegacy txn = TransactionLegacy.currentTxn();
PreparedStatement pstmt = null;
String sql = GET_LAST_USER_STATS;
try {
pstmt = txn.prepareAutoCloseStatement(sql);
ResultSet rs = pstmt.executeQuery();
if (rs.next()) {
return Long.valueOf(rs.getLong(1));
}
} catch (Exception ex) {


log.info("error getting last user stats id");
}
}

};