//,temp,sample_9184.java,2,17,temp,sample_5620.java,2,17
//,3
public class xxx {
public void dummy_method(){
TransactionLegacy txn = TransactionLegacy.currentTxn();
PreparedStatement pstmt = null;
try {
pstmt = txn.prepareAutoCloseStatement(sql);
pstmt.setLong(1, dcId);
ResultSet rs = pstmt.executeQuery();
while (rs.next()) {
l.add(new Pair<Long, Integer>(rs.getLong(1), rs.getInt(2)));
}
} catch (SQLException e) {


log.info("sqlexception");
}
}

};