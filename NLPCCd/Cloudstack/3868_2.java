//,temp,sample_5004.java,2,9,temp,sample_5003.java,2,9
//,2
public class xxx {
private void addF5ServiceProvider(Connection conn, long physicalNetworkId, long zoneId) {
String insertPNSP = "INSERT INTO `cloud`.`physical_network_service_providers` (`uuid`, `physical_network_id` , `provider_name`, `state` ," + "`destination_physical_network_id`, `vpn_service_provided`, `dhcp_service_provided`, `dns_service_provided`, `gateway_service_provided`," + "`firewall_service_provided`, `source_nat_service_provided`, `load_balance_service_provided`, `static_nat_service_provided`," + "`port_forwarding_service_provided`, `user_data_service_provided`, `security_group_service_provided`) VALUES (?,?,?,?,0,0,0,0,0,0,0,1,0,0,0,0)";
try(PreparedStatement pstmtUpdate = conn.prepareStatement(insertPNSP);) {


log.info("adding physicalnetworkserviceprovider in to physical network");
}
}

};