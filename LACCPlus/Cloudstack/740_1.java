//,temp,Upgrade305to306.java,116,132,temp,Upgrade305to306.java,77,93
//,2
public class xxx {
    private void addIndexForHostDetails(Connection conn) {

        //First drop if it exists. (Due to patches shipped to customers some will have the index and some wont.)
        List<String> indexList = new ArrayList<String>();
        s_logger.debug("Dropping index fk_host_details__host_id if it exists");
        indexList.add("fk_host_details__host_id");
        DbUpgradeUtils.dropKeysIfExist(conn, "host_details", indexList, false);

        //Now add index.
        try (PreparedStatement pstmt = conn.prepareStatement("ALTER TABLE `cloud`.`host_details` ADD INDEX `fk_host_details__host_id`(`host_id`)");) {
            pstmt.executeUpdate();
            s_logger.debug("Added index fk_host_details__host_id for table host_details");
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to add index fk_host_details__host_id to host_details table for the column host_id", e);
        }

    }

};