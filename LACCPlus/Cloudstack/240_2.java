//,temp,Upgrade2214to30.java,757,775,temp,Upgrade410to420.java,810,821
//,3
public class xxx {
    private void updateLegacyZones(Connection conn, List<Long> zones) {
        //Insert legacy zones into table for legacy zones.
        try (PreparedStatement legacyZonesQuery = conn.prepareStatement("INSERT INTO `cloud`.`legacy_zones` (zone_id) VALUES (?)");){
            for (Long zoneId : zones) {
                legacyZonesQuery.setLong(1, zoneId);
                legacyZonesQuery.executeUpdate();
                s_logger.debug("Inserted zone " + zoneId + " into cloud.legacyzones table");
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable add zones to cloud.legacyzones table.", e);
        }
    }

};