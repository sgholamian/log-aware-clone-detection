//,temp,sample_6192.java,2,17,temp,sample_3371.java,2,17
//,2
public class xxx {
public void dummy_method(){
pstmt.setLong(2, networkOfferingId);
rs = pstmt.executeQuery();
while (rs.next()) {
networkId = rs.getLong(1);
String insertLbMapping = "INSERT INTO `cloud`.`network_external_lb_device_map` (uuid, network_id, external_load_balancer_device_id, created) VALUES ( ?, ?, ?, now())";
pstmtUpdate = conn.prepareStatement(insertLbMapping);
pstmtUpdate.setString(1, UUID.randomUUID().toString());
pstmtUpdate.setLong(2, networkId);
pstmtUpdate.setLong(3, f5DeviceId);
pstmtUpdate.executeUpdate();


log.info("successfully added entry in network external lb device map for network and device id");
}
}

};