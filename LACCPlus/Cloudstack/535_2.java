//,temp,Upgrade305to306.java,134,194,temp,Upgrade301to302.java,94,158
//,3
public class xxx {
    protected void updateSharedNetworks(Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSet rs1 = null;

        try {
            pstmt =
                conn.prepareStatement("select n.id, map.id from `cloud`.`network_offerings` n, `cloud`.`ntwk_offering_service_map` map "
                    + "where n.id=map.network_offering_id and map.service='Lb' and map.provider='VirtualRouter';");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                long ntwkOffId = rs.getLong(1);
                long mapId = rs.getLong(2);

                //check if the network offering has source nat service enabled
                pstmt =
                    conn.prepareStatement("select n.id from `cloud`.`network_offerings` n, `cloud`.`ntwk_offering_service_map`"
                        + " map where n.id=map.network_offering_id and map.service='SourceNat' AND n.id=?");
                pstmt.setLong(1, ntwkOffId);
                rs1 = pstmt.executeQuery();
                if (rs1.next()) {
                    continue;
                }

                //delete the service only when there are no lb rules for the network(s) using this network offering
                pstmt =
                    conn.prepareStatement("select * from  `cloud`.`firewall_rules` f, `cloud`.`networks` n, `cloud`.`network_offerings`"
                        + " off where f.purpose='LB' and f.network_id=n.id and n.network_offering_id=off.id and off.id=?");
                pstmt.setLong(1, ntwkOffId);
                rs1 = pstmt.executeQuery();
                if (rs1.next()) {
                    continue;
                }

                //delete lb service for the network offering
                pstmt = conn.prepareStatement("DELETE FROM `cloud`.`ntwk_offering_service_map` WHERE id=?");
                pstmt.setLong(1, mapId);
                pstmt.executeUpdate();
                s_logger.debug("Deleted lb service for network offering id=" + ntwkOffId + " as it doesn't have source nat service enabled");

                //delete lb service for the network
                pstmt =
                    conn.prepareStatement("SELECT map.id, n.id FROM `cloud`.`ntwk_service_map` map, networks n WHERE n.network_offering_id=? "
                        + "AND  map.network_id=n.id AND map.service='Lb'");
                pstmt.setLong(1, ntwkOffId);
                rs1 = pstmt.executeQuery();
                while (rs1.next()) {
                    mapId = rs1.getLong(1);
                    long ntwkId = rs1.getLong(2);

                    pstmt = conn.prepareStatement("DELETE FROM `cloud`.`ntwk_service_map` WHERE id=?");
                    pstmt.setLong(1, mapId);
                    pstmt.executeUpdate();
                    s_logger.debug("Deleted lb service for network id=" + ntwkId + " as it doesn't have source nat service enabled");
                }

            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to update shared networks due to exception while executing query " + pstmt, e);
        } finally {
            closeAutoCloseable(rs);
            closeAutoCloseable(rs1);
            closeAutoCloseable(pstmt);
        }
    }

};