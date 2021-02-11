//,temp,Upgrade218to22.java,827,842,temp,Upgrade224to225.java,339,353
//,3
public class xxx {
    private long retrieveNetworkOfferingId(Connection conn, String type) throws SQLException, CloudRuntimeException {
        long networkOfferingId;
        try (
                PreparedStatement pstmt = conn.prepareStatement("SELECT id FROM network_offerings WHERE name=?");
            ) {
            pstmt.setString(1, type);
            try (ResultSet rs = pstmt.executeQuery();) {
                if (!rs.next()) {
                    s_logger.error("Unable to find the network offering for networktype '" + type + "'");
                    throw new CloudRuntimeException("Unable to find the storage network offering.");
                }
                networkOfferingId = rs.getLong(1);
                return networkOfferingId;
            }
        }
    }

};