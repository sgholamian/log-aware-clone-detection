//,temp,Upgrade227to228.java,95,130,temp,Upgrade224to225.java,82,125
//,3
public class xxx {
    private void updateDomainLevelNetworks(Connection conn) {
        s_logger.debug("Updating domain level specific networks...");
        try {
            PreparedStatement pstmt =
                conn.prepareStatement("SELECT n.id FROM networks n, network_offerings o WHERE n.shared=1 AND o.system_only=0 AND o.id=n.network_offering_id");
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Object[]> networks = new ArrayList<Object[]>();
            while (rs.next()) {
                Object[] network = new Object[10];
                network[0] = rs.getLong(1); // networkId
                networks.add(network);
            }
            rs.close();
            pstmt.close();

            for (Object[] network : networks) {
                Long networkId = (Long)network[0];
                pstmt = conn.prepareStatement("SELECT * from domain_network_ref where network_id=?");
                pstmt.setLong(1, networkId);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    s_logger.debug("Setting network id=" + networkId + " as domain specific shared network");
                    pstmt = conn.prepareStatement("UPDATE networks set is_domain_specific=1 where id=?");
                    pstmt.setLong(1, networkId);
                    pstmt.executeUpdate();
                }
                rs.close();
                pstmt.close();
            }

            s_logger.debug("Successfully updated domain level specific networks");
        } catch (SQLException e) {
            s_logger.error("Failed to set domain specific shared networks due to ", e);
            throw new CloudRuntimeException("Failed to set domain specific shared networks due to ", e);
        }
    }

};