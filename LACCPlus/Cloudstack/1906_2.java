//,temp,Upgrade410to420.java,1365,1396,temp,Upgrade410to420.java,896,925
//,3
public class xxx {
    private void createPlaceHolderNics(Connection conn) {
        try (PreparedStatement pstmt =
                     conn.prepareStatement("SELECT network_id, gateway, ip4_address FROM `cloud`.`nics` WHERE reserver_name IN ('DirectNetworkGuru','DirectPodBasedNetworkGuru') and vm_type='DomainRouter' AND removed IS null");)
        {
            try(ResultSet rs = pstmt.executeQuery();) {
                while (rs.next()) {
                    Long networkId = rs.getLong(1);
                    String gateway = rs.getString(2);
                    String ip = rs.getString(3);
                    String uuid = UUID.randomUUID().toString();
                    //Insert placeholder nic for each Domain router nic in Shared network
                    try(PreparedStatement insert_pstmt =
                            conn.prepareStatement("INSERT INTO `cloud`.`nics` (uuid, ip4_address, gateway, network_id, state, strategy, vm_type, default_nic, created) VALUES (?, ?, ?, ?, 'Reserved', 'PlaceHolder', 'DomainRouter', 0, now())");) {
                        insert_pstmt.setString(1, uuid);
                        insert_pstmt.setString(2, ip);
                        insert_pstmt.setString(3, gateway);
                        insert_pstmt.setLong(4, networkId);
                        insert_pstmt.executeUpdate();
                    }catch (SQLException e) {
                        throw new CloudRuntimeException("Unable to create placeholder nics", e);
                    }
                    s_logger.debug("Created placeholder nic for the ipAddress " + ip + " and network " + networkId);
                }
            }catch (SQLException e) {
                throw new CloudRuntimeException("Unable to create placeholder nics", e);
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to create placeholder nics", e);
        }
    }

};