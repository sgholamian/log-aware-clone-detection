//,temp,sample_2342.java,2,17,temp,sample_4217.java,2,17
//,2
public class xxx {
public void dummy_method(){
try {
txn.start();
String sql = REMOVE_BY_USERID_LBID;
pstmt = txn.prepareAutoCloseStatement(sql);
pstmt.setLong(1, accountId);
pstmt.setLong(2, lbId);
pstmt.executeUpdate();
txn.commit();
} catch (Exception e) {
txn.rollback();


log.info("error removing usageloadbalancerpolicyvo");
}
}

};