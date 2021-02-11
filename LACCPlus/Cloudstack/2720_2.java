//,temp,Upgrade30xBase.java,181,226,temp,Upgrade30xBase.java,50,108
//,3
public class xxx {
    protected long addPhysicalNetworkToZone(Connection conn, long zoneId, String zoneName, String networkType, String vnet, Long domainId) {

        String getNextNetworkSequenceSql = "SELECT value from `cloud`.`sequence` where name='physical_networks_seq'";
        String advanceNetworkSequenceSql = "UPDATE `cloud`.`sequence` set value=value+1 where name='physical_networks_seq'";
        PreparedStatement pstmtUpdate = null, pstmt2 = null;
        // add p.network
        try {
            pstmt2 = conn.prepareStatement(getNextNetworkSequenceSql);

            ResultSet rsSeq = pstmt2.executeQuery();
            rsSeq.next();

            long physicalNetworkId = rsSeq.getLong(1);
            rsSeq.close();
            pstmt2.close();
            pstmt2 = conn.prepareStatement(advanceNetworkSequenceSql);
            pstmt2.executeUpdate();
            pstmt2.close();

            String uuid = UUID.randomUUID().toString();
            String broadcastDomainRange = "POD";
            if ("Advanced".equals(networkType)) {
                broadcastDomainRange = "ZONE";
            }

            s_logger.debug("Adding PhysicalNetwork " + physicalNetworkId + " for Zone id " + zoneId);
            String sql = "INSERT INTO `cloud`.`physical_network` (id, uuid, data_center_id, vnet, broadcast_domain_range, state, name) VALUES (?,?,?,?,?,?,?)";

            pstmtUpdate = conn.prepareStatement(sql);
            pstmtUpdate.setLong(1, physicalNetworkId);
            pstmtUpdate.setString(2, uuid);
            pstmtUpdate.setLong(3, zoneId);
            pstmtUpdate.setString(4, vnet);
            pstmtUpdate.setString(5, broadcastDomainRange);
            pstmtUpdate.setString(6, "Enabled");
            zoneName = zoneName + "-pNtwk" + physicalNetworkId;
            pstmtUpdate.setString(7, zoneName);
            s_logger.warn("Statement is " + pstmtUpdate.toString());
            pstmtUpdate.executeUpdate();
            pstmtUpdate.close();

            if (domainId != null && domainId.longValue() != 0) {
                s_logger.debug("Updating domain_id for physical network id=" + physicalNetworkId);
                sql = "UPDATE `cloud`.`physical_network` set domain_id=? where id=?";
                pstmtUpdate = conn.prepareStatement(sql);
                pstmtUpdate.setLong(1, domainId);
                pstmtUpdate.setLong(2, physicalNetworkId);
                pstmtUpdate.executeUpdate();
                pstmtUpdate.close();
            }

            return physicalNetworkId;
        } catch (SQLException e) {
            throw new CloudRuntimeException("Exception while adding PhysicalNetworks", e);
        } finally {
            closeAutoCloseable(pstmt2);
            closeAutoCloseable(pstmtUpdate);
        }
    }

};