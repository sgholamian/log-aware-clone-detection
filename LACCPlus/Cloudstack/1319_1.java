//,temp,Upgrade222to224.java,261,320,temp,Upgrade302to40.java,549,581
//,3
public class xxx {
    private void updateUserStatsWithNetwork(Connection conn) {
        try {
            PreparedStatement pstmt =
                conn.prepareStatement("SELECT id, device_id FROM user_statistics WHERE network_id=0 or network_id is NULL and public_ip_address is NULL");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong(1);
                Long instanceId = rs.getLong(2);

                if (instanceId != null && instanceId.longValue() != 0) {
                    // Check if domR is already expunged; we shouldn't update user stats in this case as nics are gone too
                    pstmt = conn.prepareStatement("SELECT * from vm_instance where id=? and removed is not null");
                    pstmt.setLong(1, instanceId);
                    ResultSet rs1 = pstmt.executeQuery();

                    if (rs1.next()) {
                        s_logger.debug("Not updating user_statistics table for domR id=" + instanceId + " as domR is already expunged");
                        continue;
                    }

                    pstmt = conn.prepareStatement("SELECT network_id FROM nics WHERE instance_id=? AND mode='Dhcp'");
                    pstmt.setLong(1, instanceId);
                    ResultSet rs2 = pstmt.executeQuery();

                    if (!rs2.next()) {
                        throw new CloudRuntimeException("Failed to update user_statistics table as a part of 222 to 224 upgrade: couldn't get network_id from nics table");
                    }

                    Long networkId = rs2.getLong(1);

                    if (networkId != null) {
                        pstmt = conn.prepareStatement("UPDATE user_statistics SET network_id=?, device_type='DomainRouter' where id=?");
                        pstmt.setLong(1, networkId);
                        pstmt.setLong(2, id);
                        pstmt.executeUpdate();
                    }
                }
            }

            rs.close();
            pstmt.close();

            s_logger.debug("Upgraded user_statistics with networkId for DomainRouter device type");

            // update network_id information for ExternalFirewall and ExternalLoadBalancer device types
            PreparedStatement pstmt1 =
                conn.prepareStatement("update user_statistics us, user_ip_address uip set us.network_id = uip.network_id where us.public_ip_address = uip.public_ip_address "
                    + "and us.device_type in ('ExternalFirewall' , 'ExternalLoadBalancer')");
            pstmt1.executeUpdate();
            pstmt1.close();

            s_logger.debug("Upgraded user_statistics with networkId for ExternalFirewall and ExternalLoadBalancer device types");

            s_logger.debug("Successfully update user_statistics table with network_ids as a part of 222 to 224 upgrade");

        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to update user_statistics table with network_ids as a part of 222 to 224 upgrade", e);
        }
    }

};