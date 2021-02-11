//,temp,Upgrade2214to30.java,777,870,temp,Upgrade302to40.java,386,477
//,3
public class xxx {
    private void cloneOfferingAndAddTag(Connection conn, long networkOfferingId, long physicalNetworkId, String newTag) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("select count(*) from `cloud`.`network_offerings`");
            rs = pstmt.executeQuery();
            long ntwkOffCount = 0;
            while (rs.next()) {
                ntwkOffCount = rs.getLong(1);
            }
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement("DROP TEMPORARY TABLE IF EXISTS `cloud`.`network_offerings2`");
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement("CREATE TEMPORARY TABLE `cloud`.`network_offerings2` ENGINE=MEMORY SELECT * FROM `cloud`.`network_offerings` WHERE id=1");
            pstmt.executeUpdate();
            pstmt.close();

            // clone the record to
            pstmt = conn.prepareStatement("INSERT INTO `cloud`.`network_offerings2` SELECT * FROM `cloud`.`network_offerings` WHERE id=?");
            pstmt.setLong(1, networkOfferingId);
            pstmt.executeUpdate();
            pstmt.close();

            pstmt = conn.prepareStatement("SELECT unique_name FROM `cloud`.`network_offerings` WHERE id=?");
            pstmt.setLong(1, networkOfferingId);
            rs = pstmt.executeQuery();
            String uniqueName = null;
            while (rs.next()) {
                uniqueName = rs.getString(1) + "-" + physicalNetworkId;
            }
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement("UPDATE `cloud`.`network_offerings2` SET id=?, unique_name=?, name=?, tags=?, uuid=?  WHERE id=?");
            ntwkOffCount = ntwkOffCount + 1;
            long newNetworkOfferingId = ntwkOffCount;
            pstmt.setLong(1, newNetworkOfferingId);
            pstmt.setString(2, uniqueName);
            pstmt.setString(3, uniqueName);
            pstmt.setString(4, newTag);
            String uuid = UUID.randomUUID().toString();
            pstmt.setString(5, uuid);
            pstmt.setLong(6, networkOfferingId);
            pstmt.executeUpdate();
            pstmt.close();

            pstmt = conn.prepareStatement("INSERT INTO `cloud`.`network_offerings` SELECT * from `cloud`.`network_offerings2` WHERE id=" + newNetworkOfferingId);
            pstmt.executeUpdate();
            pstmt.close();

            //clone service map
            pstmt = conn.prepareStatement("select service, provider from `cloud`.`ntwk_offering_service_map` where network_offering_id=?");
            pstmt.setLong(1, networkOfferingId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String service = rs.getString(1);
                String provider = rs.getString(2);
                pstmt =
                    conn.prepareStatement("INSERT INTO `cloud`.`ntwk_offering_service_map` (`network_offering_id`, `service`, `provider`, `created`) values (?,?,?, now())");
                pstmt.setLong(1, newNetworkOfferingId);
                pstmt.setString(2, service);
                pstmt.setString(3, provider);
                pstmt.executeUpdate();
            }
            rs.close();
            pstmt.close();

            pstmt =
                conn.prepareStatement("UPDATE `cloud`.`networks` SET network_offering_id=? where physical_network_id=? and traffic_type ='Guest' and network_offering_id=" +
                    networkOfferingId);
            pstmt.setLong(1, newNetworkOfferingId);
            pstmt.setLong(2, physicalNetworkId);
            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException e) {
            throw new CloudRuntimeException("Exception while cloning NetworkOffering", e);
        } finally {
            closeAutoCloseable(rs);
            try {
                pstmt = conn.prepareStatement("DROP TEMPORARY TABLE `cloud`.`network_offerings2`");
                pstmt.executeUpdate();
            } catch (SQLException e) {
                s_logger.info("[ignored] ",e);
            }
            closeAutoCloseable(pstmt);
        }
    }

};