//,temp,sample_4996.java,2,12,temp,sample_3361.java,2,12
//,3
public class xxx {
private void addHostDetailsIndex(Connection conn) {
try(PreparedStatement pstmt = conn.prepareStatement("SHOW INDEX FROM `cloud`.`host_details` where KEY_NAME = 'fk_host_details__host_id'");) {
try(ResultSet rs = pstmt.executeQuery();) {
if (rs.next()) {


log.info("index already exists on host details not adding new one");
}
}
}
}

};