//,temp,Upgrade2214to30.java,1065,1169,temp,Upgrade2214to30.java,777,870
//,3
public class xxx {
    protected String fixNetworksWithExternalDevices(Connection conn) {
        List<PreparedStatement> pstmt2Close = new ArrayList<PreparedStatement>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSet rs1 = null;

        //Get zones to upgrade
        List<Long> zoneIds = new ArrayList<Long>();
        try {
            pstmt =
                conn.prepareStatement("select id from `cloud`.`data_center` where lb_provider='F5BigIp' or firewall_provider='JuniperSRX' or gateway_provider='JuniperSRX'");
            pstmt2Close.add(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                zoneIds.add(rs.getLong(1));
            }
        } catch (SQLException e) {
            TransactionLegacy.closePstmts(pstmt2Close);
            throw new CloudRuntimeException("Unable to switch networks to the new network offering", e);
        }

        String uniqueName = null;
        HashMap<Long, Long> newNetworkOfferingMap = new HashMap<Long, Long>();

        for (Long zoneId : zoneIds) {
            try {
                // Find the correct network offering
                pstmt = conn.prepareStatement("select id, network_offering_id from `cloud`.`networks` where guest_type='Virtual' and data_center_id=?");
                pstmt2Close.add(pstmt);
                pstmt.setLong(1, zoneId);
                rs = pstmt.executeQuery();
                pstmt = conn.prepareStatement("select count(*) from `cloud`.`network_offerings`");
                rs1 = pstmt.executeQuery();
                long ntwkOffCount = 0;
                while (rs1.next()) {
                    ntwkOffCount = rs1.getLong(1);
                }

                pstmt = conn.prepareStatement("CREATE TEMPORARY TABLE `cloud`.`network_offerings2` ENGINE=MEMORY SELECT * FROM `cloud`.`network_offerings` WHERE id=1");
                pstmt2Close.add(pstmt);
                pstmt.executeUpdate();

                while (rs.next()) {
                    long networkId = rs.getLong(1);
                    long networkOfferingId = rs.getLong(2);
                    s_logger.debug("Updating network offering for the network id=" + networkId + " as it has switch_to_isolated=1");
                    Long newNetworkOfferingId = null;
                    if (!newNetworkOfferingMap.containsKey(networkOfferingId)) {
                        uniqueName = "Isolated with external providers";
                        // clone the record to
                        pstmt = conn.prepareStatement("INSERT INTO `cloud`.`network_offerings2` SELECT * FROM `cloud`.`network_offerings` WHERE id=?");
                        pstmt2Close.add(pstmt);
                        pstmt.setLong(1, networkOfferingId);
                        pstmt.executeUpdate();

                        //set the new unique name
                        pstmt = conn.prepareStatement("UPDATE `cloud`.`network_offerings2` SET id=?, unique_name=?, name=? WHERE id=?");
                        pstmt2Close.add(pstmt);
                        ntwkOffCount = ntwkOffCount + 1;
                        newNetworkOfferingId = ntwkOffCount;
                        pstmt.setLong(1, newNetworkOfferingId);
                        pstmt.setString(2, uniqueName);
                        pstmt.setString(3, uniqueName);
                        pstmt.setLong(4, networkOfferingId);
                        pstmt.executeUpdate();

                        pstmt =
                            conn.prepareStatement("INSERT INTO `cloud`.`network_offerings` SELECT * from " + "`cloud`.`network_offerings2` WHERE id=" +
                                newNetworkOfferingId);
                        pstmt2Close.add(pstmt);
                        pstmt.executeUpdate();

                        pstmt = conn.prepareStatement("UPDATE `cloud`.`networks` SET network_offering_id=? where id=?");
                        pstmt2Close.add(pstmt);
                        pstmt.setLong(1, newNetworkOfferingId);
                        pstmt.setLong(2, networkId);
                        pstmt.executeUpdate();

                        newNetworkOfferingMap.put(networkOfferingId, ntwkOffCount);
                    } else {
                        pstmt = conn.prepareStatement("UPDATE `cloud`.`networks` SET network_offering_id=? where id=?");
                        pstmt2Close.add(pstmt);
                        newNetworkOfferingId = newNetworkOfferingMap.get(networkOfferingId);
                        pstmt.setLong(1, newNetworkOfferingId);
                        pstmt.setLong(2, networkId);
                        pstmt.executeUpdate();
                    }

                    s_logger.debug("Successfully updated network id=" + networkId + " with new network offering id " + newNetworkOfferingId);
                }

            } catch (SQLException e) {
                throw new CloudRuntimeException("Unable to switch networks to the new network offering", e);
            } finally {
                try (PreparedStatement dropStatement = conn.prepareStatement("DROP TABLE `cloud`.`network_offerings2`");){
                    dropStatement.executeUpdate();
                } catch (SQLException e) {
                    s_logger.info("[ignored]",e);
                }
                TransactionLegacy.closePstmts(pstmt2Close);
            }
        }

        return uniqueName;
    }

};