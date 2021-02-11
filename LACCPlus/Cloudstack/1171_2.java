//,temp,VMTemplatePoolDaoImpl.java,188,213,temp,Upgrade222to224.java,109,140
//,3
public class xxx {
    private void checkForDuplicatePublicNetworks(Connection conn) {
        try {
            // There should be one public network per zone
            PreparedStatement pstmt = conn.prepareStatement("SELECT id FROM `cloud`.`data_center`");
            ResultSet zones = pstmt.executeQuery();
            ArrayList<Long> zonesWithDuplicateNetworks = new ArrayList<Long>();
            String errorMsg = "Found zones with duplicate public networks during 222 to 224 upgrade. Zone IDs: ";
            long zoneId;

            while (zones.next()) {
                zoneId = zones.getLong(1);
                pstmt = conn.prepareStatement("SELECT count(*) FROM `cloud`.`networks` WHERE `networks`.`traffic_type`='Public' AND `data_center_id`=?");
                pstmt.setLong(1, zoneId);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    long numNetworks = rs.getLong(1);
                    if (numNetworks > 1) {
                        zonesWithDuplicateNetworks.add(zoneId);
                    }
                }
            }

            if (zonesWithDuplicateNetworks.size() > 0) {
                s_logger.warn(errorMsg + zonesWithDuplicateNetworks);
            }

        } catch (SQLException e) {
            s_logger.warn(e);
            throw new CloudRuntimeException("Unable to check for duplicate public networks as part of 222 to 224 upgrade.");
        }
    }

};