//,temp,Upgrade410to420.java,1521,1538,temp,Upgrade410to420.java,1500,1519
//,3
public class xxx {
    private void addF5ServiceProvider(Connection conn, long physicalNetworkId, long zoneId) {
        String insertPNSP =
                "INSERT INTO `cloud`.`physical_network_service_providers` (`uuid`, `physical_network_id` , `provider_name`, `state` ,"
                        + "`destination_physical_network_id`, `vpn_service_provided`, `dhcp_service_provided`, `dns_service_provided`, `gateway_service_provided`,"
                        + "`firewall_service_provided`, `source_nat_service_provided`, `load_balance_service_provided`, `static_nat_service_provided`,"
                        + "`port_forwarding_service_provided`, `user_data_service_provided`, `security_group_service_provided`) VALUES (?,?,?,?,0,0,0,0,0,0,0,1,0,0,0,0)";
        try(PreparedStatement pstmtUpdate = conn.prepareStatement(insertPNSP);) {
            // add physical network service provider - F5BigIp
            s_logger.debug("Adding PhysicalNetworkServiceProvider F5BigIp" + " in to physical network" + physicalNetworkId);
            pstmtUpdate.setString(1, UUID.randomUUID().toString());
            pstmtUpdate.setLong(2, physicalNetworkId);
            pstmtUpdate.setString(3, "F5BigIp");
            pstmtUpdate.setString(4, "Enabled");
            pstmtUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new CloudRuntimeException("Exception while adding PhysicalNetworkServiceProvider F5BigIp", e);
        }
    }

};