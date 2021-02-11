//,temp,sample_7940.java,2,17,temp,sample_6740.java,2,17
//,3
public class xxx {
public void dummy_method(){
TransactionLegacy txn = TransactionLegacy.currentTxn();
PreparedStatement pstmt = null;
String sql = GET_LAST_JOB_SUCCESS_DATE_MILLIS;
try {
pstmt = txn.prepareAutoCloseStatement(sql);
ResultSet rs = pstmt.executeQuery();
if (rs.next()) {
return rs.getLong(1);
}
} catch (Exception ex) {


log.info("error getting last usage job success date");
}
}

};