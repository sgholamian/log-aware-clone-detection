//,temp,Upgrade301to302.java,94,158,temp,Upgrade302to40.java,500,547
//,3
public class xxx {
    private void addVpcProvider(Connection conn) {
        //Encrypt config params and change category to Hidden
        s_logger.debug("Adding vpc provider to all physical networks in the system");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("SELECT id FROM `cloud`.`physical_network` WHERE removed is NULL");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Long pNtwkId = rs.getLong(1);

                //insert provider
                pstmt =
                    conn.prepareStatement("INSERT INTO `cloud`.`physical_network_service_providers` "
                        + "(`physical_network_id`, `provider_name`, `state`, `vpn_service_provided`, `dhcp_service_provided`, "
                        + "`dns_service_provided`, `gateway_service_provided`, `firewall_service_provided`, `source_nat_service_provided`,"
                        + " `load_balance_service_provided`, `static_nat_service_provided`, `port_forwarding_service_provided`,"
                        + " `user_data_service_provided`, `security_group_service_provided`) "
                        + "VALUES (?, 'VpcVirtualRouter', 'Enabled', 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0)");

                pstmt.setLong(1, pNtwkId);
                pstmt.executeUpdate();

                //get provider id
                pstmt =
                    conn.prepareStatement("SELECT id FROM `cloud`.`physical_network_service_providers` "
                        + "WHERE physical_network_id=? and provider_name='VpcVirtualRouter'");
                pstmt.setLong(1, pNtwkId);
                ResultSet rs1 = pstmt.executeQuery();
                rs1.next();
                long providerId = rs1.getLong(1);

                //insert VR element
                pstmt = conn.prepareStatement("INSERT INTO `cloud`.`virtual_router_providers` (`nsp_id`, `type`, `enabled`) " + "VALUES (?, 'VPCVirtualRouter', 1)");
                pstmt.setLong(1, providerId);
                pstmt.executeUpdate();

                s_logger.debug("Added VPC Virtual router provider for physical network id=" + pNtwkId);

            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable add VPC physical network service provider ", e);
        } finally {
            closeAutoCloseable(rs);
            closeAutoCloseable(pstmt);
        }
        s_logger.debug("Done adding VPC physical network service providers to all physical networks");
    }

};