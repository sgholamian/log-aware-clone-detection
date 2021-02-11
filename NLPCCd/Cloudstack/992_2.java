//,temp,sample_4996.java,2,12,temp,sample_3361.java,2,12
//,3
public class xxx {
private void addHostDetailsUniqueKey(Connection conn) {
try ( PreparedStatement pstmt = conn.prepareStatement("SHOW INDEX FROM `cloud`.`host_details` WHERE KEY_NAME = 'uk_host_id_name'");
ResultSet rs = pstmt.executeQuery();
) {
if (rs.next()) {


log.info("unique key already exists on host details not adding new one");
}
}
}

};