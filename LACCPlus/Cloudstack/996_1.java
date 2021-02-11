//,temp,Upgrade227to228Premium.java,53,82,temp,Upgrade222to224.java,322,338
//,3
public class xxx {
    private void addSourceIdColumn(Connection conn) {
        boolean insertField = false;
        try {
            PreparedStatement pstmt;
            try {
                pstmt = conn.prepareStatement("SELECT source_id FROM `cloud_usage`.`usage_storage`");
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    s_logger.info("The source id field already exist, not adding it");
                }

            } catch (Exception e) {
                // if there is an exception, it means that field doesn't exist, and we can create it
                insertField = true;
            }

            if (insertField) {
                s_logger.debug("Adding source_id to usage_storage...");
                pstmt = conn.prepareStatement("ALTER TABLE `cloud_usage`.`usage_storage` ADD COLUMN `source_id` bigint unsigned");
                pstmt.executeUpdate();
                s_logger.debug("Column source_id was added successfully to usage_storage table");
                pstmt.close();
            }

        } catch (SQLException e) {
            s_logger.error("Failed to add source_id to usage_storage due to ", e);
            throw new CloudRuntimeException("Failed to add source_id to usage_storage due to ", e);
        }
    }

};