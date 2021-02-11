//,temp,sample_546.java,2,17,temp,sample_8457.java,2,17
//,2
public class xxx {
public void dummy_method(){
String sql = DELETE_OLD_STATS;
PreparedStatement pstmt = null;
try {
txn.start();
pstmt = txn.prepareAutoCloseStatement(sql);
pstmt.setLong(1, maxEventTime);
pstmt.executeUpdate();
txn.commit();
} catch (Exception ex) {
txn.rollback();


log.info("error deleting old usage disk stats");
}
}

};