//,temp,sample_6193.java,2,16,temp,sample_3372.java,2,16
//,2
public class xxx {
public void dummy_method(){
pstmtUpdate.setString(1, UUID.randomUUID().toString());
pstmtUpdate.setLong(2, networkId);
pstmtUpdate.setLong(3, f5DeviceId);
pstmtUpdate.executeUpdate();
String insertFwMapping = "INSERT INTO `cloud`.`network_external_firewall_device_map` (uuid, network_id, external_firewall_device_id, created) VALUES ( ?, ?, ?, now())";
pstmtUpdate = conn.prepareStatement(insertFwMapping);
pstmtUpdate.setString(1, UUID.randomUUID().toString());
pstmtUpdate.setLong(2, networkId);
pstmtUpdate.setLong(3, srxDevivceId);
pstmtUpdate.executeUpdate();


log.info("successfully added entry in network external firewall device map for network and srx device id");
}

};