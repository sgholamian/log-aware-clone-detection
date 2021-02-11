//,temp,Upgrade2214to30.java,903,1010,temp,Upgrade2214to30.java,777,870
//,3
public class xxx {
    protected void switchAccountSpecificNetworksToIsolated(Connection conn) {
        List<PreparedStatement> pstmt2Close = new ArrayList<PreparedStatement>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        try {
            //check if switch_to_isolated is present; if not - skip this part of the code
            try {
                pstmt = conn.prepareStatement("select switch_to_isolated from `cloud`.`networks`");
                pstmt2Close.add(pstmt);
                rs = pstmt.executeQuery();
            } catch (Exception ex) {
                s_logger.debug("switch_to_isolated field is not present in networks table");
                if (pstmt != null) {
                    pstmt.close();
                }
                return;
            }

            // get all networks that need to be updated to the isolated network offering
            pstmt = conn.prepareStatement("select id, network_offering_id from `cloud`.`networks` where switch_to_isolated=1");
            pstmt2Close.add(pstmt);
            rs = pstmt.executeQuery();

            pstmt = conn.prepareStatement("select count(*) from `cloud`.`network_offerings`");
            pstmt2Close.add(pstmt);
            rs1 = pstmt.executeQuery();
            long ntwkOffCount = 0;
            while (rs1.next()) {
                ntwkOffCount = rs1.getLong(1);
            }

            s_logger.debug("Have " + ntwkOffCount + " networkOfferings");
            pstmt = conn.prepareStatement("CREATE TEMPORARY TABLE `cloud`.`network_offerings2` ENGINE=MEMORY SELECT * FROM `cloud`.`network_offerings` WHERE id=1");
            pstmt2Close.add(pstmt);
            pstmt.executeUpdate();

            HashMap<Long, Long> newNetworkOfferingMap = new HashMap<Long, Long>();

            while (rs.next()) {
                long networkId = rs.getLong(1);
                long networkOfferingId = rs.getLong(2);
                s_logger.debug("Updating network offering for the network id=" + networkId + " as it has switch_to_isolated=1");
                Long newNetworkOfferingId = null;

                if (!newNetworkOfferingMap.containsKey(networkOfferingId)) {
                    // clone the record to
                    pstmt = conn.prepareStatement("INSERT INTO `cloud`.`network_offerings2` SELECT * FROM `cloud`.`network_offerings` WHERE id=?");
                    pstmt2Close.add(pstmt);
                    pstmt.setLong(1, networkOfferingId);
                    pstmt.executeUpdate();

                    pstmt = conn.prepareStatement("UPDATE `cloud`.`network_offerings2` SET id=?, guest_type='Isolated', unique_name=?, name=? WHERE id=?");
                    pstmt2Close.add(pstmt);
                    ntwkOffCount = ntwkOffCount + 1;
                    newNetworkOfferingId = ntwkOffCount;
                    String uniqueName = "Isolated w/o source nat";
                    pstmt.setLong(1, newNetworkOfferingId);
                    pstmt.setString(2, uniqueName);
                    pstmt.setString(3, uniqueName);
                    pstmt.setLong(4, networkOfferingId);
                    pstmt.executeUpdate();

                    pstmt = conn.prepareStatement("INSERT INTO `cloud`.`network_offerings` SELECT * from `cloud`.`network_offerings2` WHERE id=" + newNetworkOfferingId);
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

                s_logger.debug("Successfully updated network offering id=" + networkId + " with new network offering id " + newNetworkOfferingId);
            }

            try {
                pstmt = conn.prepareStatement("ALTER TABLE `cloud`.`networks` DROP COLUMN `switch_to_isolated`");
                pstmt2Close.add(pstmt);
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                // do nothing here
                s_logger.debug("Caught SQLException when trying to drop switch_to_isolated column ", ex);
            }

        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to switch networks to isolated", e);
        } finally {
            try {
                pstmt = conn.prepareStatement("DROP TABLE `cloud`.`network_offerings2`");
                pstmt.executeUpdate();
                pstmt.close();
            } catch (SQLException e) {
                s_logger.info("[ignored]",e);
            }
            TransactionLegacy.closePstmts(pstmt2Close);
        }
    }

};