//,temp,Upgrade450to451.java,104,127,temp,Upgrade302to40.java,549,581
//,3
public class xxx {
    private void updateRouterNetworkRef(Connection conn) {
        //Encrypt config params and change category to Hidden
        s_logger.debug("Updating router network ref");
        try (
                PreparedStatement pstmt = conn.prepareStatement("SELECT d.id, d.network_id FROM `cloud`.`domain_router` d, `cloud`.`vm_instance` v " + "WHERE d.id=v.id AND v.removed is NULL");
                PreparedStatement pstmt1 = conn.prepareStatement("SELECT guest_type from `cloud`.`networks` where id=?");
                PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO `cloud`.`router_network_ref` (router_id, network_id, guest_type) " + "VALUES (?, ?, ?)");
                ResultSet rs = pstmt.executeQuery();
            ){
            while (rs.next()) {
                Long routerId = rs.getLong(1);
                Long networkId = rs.getLong(2);

                //get the network type
                pstmt1.setLong(1, networkId);
                try (ResultSet rs1 = pstmt1.executeQuery();) {
                    rs1.next();
                    String networkType = rs1.getString(1);

                    //insert the reference
                    pstmt2.setLong(1, routerId);
                    pstmt2.setLong(2, networkId);
                    pstmt2.setString(3, networkType);
                    pstmt2.executeUpdate();
                }
                s_logger.debug("Added reference for router id=" + routerId + " and network id=" + networkId);

            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Failed to update the router/network reference ", e);
        }
        s_logger.debug("Done updating router/network references");
    }

};