//,temp,Upgrade40to41.java,79,95,temp,Upgrade307to410.java,65,81
//,2
public class xxx {
    private void updateRegionEntries(Connection conn) {
        final Properties dbProps = DbProperties.getDbProperties();
        int region_id = 1;
        String regionId = dbProps.getProperty("region.id");
        if (regionId != null) {
            region_id = Integer.parseInt(regionId);
        }
        try (PreparedStatement pstmt = conn.prepareStatement("update `cloud`.`region` set id = ?");){
            //Update regionId in region table
            s_logger.debug("Updating region table with Id: " + region_id);
            pstmt.setInt(1, region_id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new CloudRuntimeException("Error while updating region entries", e);
        }
    }

};