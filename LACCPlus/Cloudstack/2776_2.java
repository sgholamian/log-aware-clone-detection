//,temp,Upgrade2214to30.java,903,1010,temp,Upgrade2214to30.java,777,870
//,3
public class xxx {
    protected void updateReduntantRouters(Connection conn) {
        List<PreparedStatement> pstmt2Close = new ArrayList<PreparedStatement>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        try {
            // get all networks that need to be updated to the redundant network offerings
            pstmt =
                conn.prepareStatement("select ni.network_id, n.network_offering_id from `cloud`.`nics` ni, `cloud`.`networks` n where ni.instance_id in (select id from `cloud`.`domain_router` where is_redundant_router=1) and n.id=ni.network_id and n.traffic_type='Guest'");
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
                s_logger.debug("Updating network offering for the network id=" + networkId + " as it has redundant routers");
                Long newNetworkOfferingId = null;

                if (!newNetworkOfferingMap.containsKey(networkOfferingId)) {
                    // clone the record to
                    pstmt = conn.prepareStatement("INSERT INTO `cloud`.`network_offerings2` SELECT * FROM `cloud`.`network_offerings` WHERE id=?");
                    pstmt2Close.add(pstmt);
                    pstmt.setLong(1, networkOfferingId);
                    pstmt.executeUpdate();

                    pstmt = conn.prepareStatement("SELECT unique_name FROM `cloud`.`network_offerings` WHERE id=?");
                    pstmt2Close.add(pstmt);
                    pstmt.setLong(1, networkOfferingId);
                    rs1 = pstmt.executeQuery();
                    String uniqueName = null;
                    while (rs1.next()) {
                        uniqueName = rs1.getString(1) + "-redundant";
                    }

                    pstmt = conn.prepareStatement("UPDATE `cloud`.`network_offerings2` SET id=?, redundant_router_service=1, unique_name=?, name=? WHERE id=?");
                    pstmt2Close.add(pstmt);
                    ntwkOffCount = ntwkOffCount + 1;
                    newNetworkOfferingId = ntwkOffCount;
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

        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to update redundant router networks", e);
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