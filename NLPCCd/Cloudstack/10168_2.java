//,temp,sample_9184.java,2,17,temp,sample_5620.java,2,17
//,3
public class xxx {
public void dummy_method(){
TransactionLegacy txn = TransactionLegacy.currentTxn();
PreparedStatement pstmt = null;
try {
pstmt = txn.prepareAutoCloseStatement(sql);
pstmt.setLong(1, hostId);
ResultSet rs = pstmt.executeQuery();
while (rs.next()) {
l.add(rs.getLong(1));
}
} catch (SQLException e) {


log.info("exception");
}
}

};