//,temp,sample_5364.java,2,17,temp,sample_2718.java,2,17
//,2
public class xxx {
public void dummy_method(){
try {
String sql = ACTIVE_AND_RECENTLY_DELETED_SEARCH + " LIMIT " + startIndex + "," + limit;
PreparedStatement pstmt = null;
pstmt = txn.prepareAutoCloseStatement(sql);
pstmt.setString(1, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), minRemovedDate));
ResultSet rs = pstmt.executeQuery();
while (rs.next()) {
vmDiskStats.add(toEntityBean(rs, false));
}
} catch (Exception ex) {


log.info("error saving vm disk stats to cloud usage db");
}
}

};