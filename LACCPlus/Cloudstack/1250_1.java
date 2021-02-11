//,temp,Upgrade2214to30.java,723,755,temp,Upgrade2214to30.java,453,497
//,3
public class xxx {
    protected void createNetworkServices(Connection conn) {
        List<PreparedStatement> pstmt2Close = new ArrayList<PreparedStatement>();
        PreparedStatement pstmt = null;
        ResultSet networkRs = null;
        ResultSet offeringRs = null;
        try {
            pstmt = conn.prepareStatement("select id, network_offering_id from `cloud`.`networks` where traffic_type='Guest'");
            pstmt2Close.add(pstmt);
            networkRs = pstmt.executeQuery();
            while (networkRs.next()) {
                long networkId = networkRs.getLong(1);
                long networkOfferingId = networkRs.getLong(2);
                pstmt = conn.prepareStatement("select service, provider from `cloud`.`ntwk_offering_service_map` where network_offering_id=?");
                pstmt2Close.add(pstmt);
                pstmt.setLong(1, networkOfferingId);
                offeringRs = pstmt.executeQuery();
                while (offeringRs.next()) {
                    String service = offeringRs.getString(1);
                    String provider = offeringRs.getString(2);
                    pstmt = conn.prepareStatement("INSERT INTO `cloud`.`ntwk_service_map` (`network_id`, `service`, `provider`, `created`) values (?,?,?, now())");
                    pstmt.setLong(1, networkId);
                    pstmt.setString(2, service);
                    pstmt.setString(3, provider);
                    pstmt.executeUpdate();
                }
                s_logger.debug("Created service/provider map for network id=" + networkId);
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to create service/provider map for networks", e);
        } finally {
            TransactionLegacy.closePstmts(pstmt2Close);
        }
    }

};