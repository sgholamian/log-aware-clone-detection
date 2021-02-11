//,temp,Upgrade301to302.java,94,158,temp,Upgrade304to305.java,183,220
//,3
public class xxx {
    private void updateRouterNetworkRef(Connection conn) {
        //Encrypt config params and change category to Hidden
        s_logger.debug("Updating router network ref");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("SELECT d.id, d.network_id FROM `cloud`.`domain_router` d, `cloud`.`vm_instance` v " + "WHERE d.id=v.id AND v.removed is NULL");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Long routerId = rs.getLong(1);
                Long networkId = rs.getLong(2);

                //get the network type
                pstmt = conn.prepareStatement("SELECT guest_type from `cloud`.`networks` where id=?");
                pstmt.setLong(1, networkId);
                ResultSet rs1 = pstmt.executeQuery();
                rs1.next();
                String networkType = rs1.getString(1);

                //insert the reference
                pstmt = conn.prepareStatement("INSERT INTO `cloud`.`router_network_ref` (router_id, network_id, guest_type) " + "VALUES (?, ?, ?)");

                pstmt.setLong(1, routerId);
                pstmt.setLong(2, networkId);
                pstmt.setString(3, networkType);
                pstmt.executeUpdate();

                s_logger.debug("Added reference for router id=" + routerId + " and network id=" + networkId);

            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Failed to update the router/network reference ", e);
        } finally {
            closeAutoCloseable(rs);
            closeAutoCloseable(pstmt);
        }
        s_logger.debug("Done updating router/network references");
    }

};