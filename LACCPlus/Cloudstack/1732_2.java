//,temp,Upgrade227to228Premium.java,53,82,temp,Upgrade410to420.java,1294,1316
//,3
public class xxx {
    private void addHostDetailsIndex(Connection conn) {
        s_logger.debug("Checking if host_details index exists, if not we will add it");
        try(PreparedStatement pstmt = conn.prepareStatement("SHOW INDEX FROM `cloud`.`host_details` where KEY_NAME = 'fk_host_details__host_id'");)
        {
            try(ResultSet rs = pstmt.executeQuery();) {
                if (rs.next()) {
                    s_logger.debug("Index already exists on host_details - not adding new one");
                } else {
                    // add the index
                    try(PreparedStatement pstmtUpdate = conn.prepareStatement("ALTER TABLE `cloud`.`host_details` ADD INDEX `fk_host_details__host_id` (`host_id`)");) {
                        pstmtUpdate.executeUpdate();
                        s_logger.debug("Index did not exist on host_details -  added new one");
                    }catch (SQLException e) {
                        throw new CloudRuntimeException("Failed to check/update the host_details index ", e);
                    }
                }
            }catch (SQLException e) {
                throw new CloudRuntimeException("Failed to check/update the host_details index ", e);
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Failed to check/update the host_details index ", e);
        }
    }

};