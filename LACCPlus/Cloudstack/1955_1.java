//,temp,Upgrade227to228Premium.java,84,121,temp,Upgrade224to225.java,82,125
//,3
public class xxx {
    private void addNetworkIdsToUserStats(Connection conn) {
        s_logger.debug("Adding network IDs to user stats...");
        try {
            String stmt = "SELECT DISTINCT public_ip_address FROM `cloud`.`user_statistics` WHERE public_ip_address IS NOT null";
            PreparedStatement pstmt = conn.prepareStatement(stmt);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String publicIpAddress = rs.getString(1);
                stmt = "SELECT network_id FROM `cloud`.`user_ip_address` WHERE public_ip_address = ?";
                pstmt = conn.prepareStatement(stmt);
                pstmt.setString(1, publicIpAddress);
                ResultSet rs2 = pstmt.executeQuery();

                if (rs2.next()) {
                    Long networkId = rs2.getLong(1);
                    String[] dbs = {"cloud", "cloud_usage"};
                    for (String db : dbs) {
                        stmt = "UPDATE `" + db + "`.`user_statistics` SET network_id = ? WHERE public_ip_address = ?";
                        pstmt = conn.prepareStatement(stmt);
                        pstmt.setLong(1, networkId);
                        pstmt.setString(2, publicIpAddress);
                        pstmt.executeUpdate();
                    }
                }

                rs2.close();
            }

            rs.close();
            pstmt.close();
            s_logger.debug("Successfully added network IDs to user stats.");
        } catch (SQLException e) {
            String errorMsg = "Failed to add network IDs to user stats.";
            s_logger.error(errorMsg, e);
            throw new CloudRuntimeException(errorMsg, e);
        }
    }

};