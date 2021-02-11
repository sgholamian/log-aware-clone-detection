//,temp,Upgrade30xBase.java,181,226,temp,Upgrade30xBase.java,50,108
//,3
public class xxx {
    protected void addDefaultVRProvider(Connection conn, long physicalNetworkId, long zoneId) {
        PreparedStatement pstmtUpdate = null, pstmt2 = null;
        try {
            // add physical network service provider - VirtualRouter
            s_logger.debug("Adding PhysicalNetworkServiceProvider VirtualRouter");
            String insertPNSP =
                "INSERT INTO `cloud`.`physical_network_service_providers` (`uuid`, `physical_network_id` , `provider_name`, `state` ,"
                    + "`destination_physical_network_id`, `vpn_service_provided`, `dhcp_service_provided`, `dns_service_provided`, `gateway_service_provided`,"
                    + "`firewall_service_provided`, `source_nat_service_provided`, `load_balance_service_provided`, `static_nat_service_provided`,"
                    + "`port_forwarding_service_provided`, `user_data_service_provided`, `security_group_service_provided`) VALUES (?,?,?,?,0,1,1,1,1,1,1,1,1,1,1,0)";

            String routerUUID = UUID.randomUUID().toString();
            pstmtUpdate = conn.prepareStatement(insertPNSP);
            pstmtUpdate.setString(1, routerUUID);
            pstmtUpdate.setLong(2, physicalNetworkId);
            pstmtUpdate.setString(3, "VirtualRouter");
            pstmtUpdate.setString(4, "Enabled");
            pstmtUpdate.executeUpdate();
            pstmtUpdate.close();

            // add virtual_router_element
            String fetchNSPid =
                "SELECT id from `cloud`.`physical_network_service_providers` where physical_network_id=" + physicalNetworkId +
                    " AND provider_name = 'VirtualRouter' AND uuid = ?";
            pstmt2 = conn.prepareStatement(fetchNSPid);
            pstmt2.setString(1, routerUUID);
            ResultSet rsNSPid = pstmt2.executeQuery();
            rsNSPid.next();
            long nspId = rsNSPid.getLong(1);
            pstmt2.close();

            String insertRouter = "INSERT INTO `cloud`.`virtual_router_providers` (`nsp_id`, `uuid` , `type` , `enabled`) " + "VALUES (?,?,?,?)";
            pstmtUpdate = conn.prepareStatement(insertRouter);
            pstmtUpdate.setLong(1, nspId);
            pstmtUpdate.setString(2, UUID.randomUUID().toString());
            pstmtUpdate.setString(3, "VirtualRouter");
            pstmtUpdate.setInt(4, 1);
            pstmtUpdate.executeUpdate();
            pstmtUpdate.close();
        } catch (SQLException e) {
            throw new CloudRuntimeException("Exception while adding PhysicalNetworks", e);
        } finally {
            closeAutoCloseable(pstmt2);
            closeAutoCloseable(pstmtUpdate);
        }
    }

};