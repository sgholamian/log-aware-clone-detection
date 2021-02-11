//,temp,Upgrade302to303.java,218,240,temp,Upgrade302to40.java,758,775
//,3
public class xxx {
    private void addSrxServiceProvider(Connection conn, long physicalNetworkId, long zoneId) {
        // add physical network service provider - JuniperSRX
        s_logger.debug("Adding PhysicalNetworkServiceProvider JuniperSRX");
        String insertPNSP =
            "INSERT INTO `cloud`.`physical_network_service_providers` (`uuid`, `physical_network_id` , `provider_name`, `state` ,"
                + "`destination_physical_network_id`, `vpn_service_provided`, `dhcp_service_provided`, `dns_service_provided`, `gateway_service_provided`,"
                + "`firewall_service_provided`, `source_nat_service_provided`, `load_balance_service_provided`, `static_nat_service_provided`,"
                + "`port_forwarding_service_provided`, `user_data_service_provided`, `security_group_service_provided`) VALUES (?,?,?,?,0,0,0,0,1,1,1,0,1,1,0,0)";
        try (PreparedStatement pstmtUpdate = conn.prepareStatement(insertPNSP);) {
            pstmtUpdate.setString(1, UUID.randomUUID().toString());
            pstmtUpdate.setLong(2, physicalNetworkId);
            pstmtUpdate.setString(3, "JuniperSRX");
            pstmtUpdate.setString(4, "Enabled");
            pstmtUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new CloudRuntimeException("Exception while adding PhysicalNetworkServiceProvider JuniperSRX", e);
        }
    }

};