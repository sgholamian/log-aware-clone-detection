//,temp,Upgrade222to224.java,62,75,temp,Upgrade302to40.java,479,498
//,3
public class xxx {
    private void addHostDetailsUniqueKey(Connection conn) {
        s_logger.debug("Checking if host_details unique key exists, if not we will add it");
        try (
                PreparedStatement pstmt = conn.prepareStatement("SHOW INDEX FROM `cloud`.`host_details` WHERE KEY_NAME = 'uk_host_id_name'");
                ResultSet rs = pstmt.executeQuery();
            ) {
            if (rs.next()) {
                s_logger.debug("Unique key already exists on host_details - not adding new one");
            } else {
                //add the key
                PreparedStatement pstmtUpdate =
                    conn.prepareStatement("ALTER IGNORE TABLE `cloud`.`host_details` ADD CONSTRAINT UNIQUE KEY `uk_host_id_name` (`host_id`, `name`)");
                pstmtUpdate.executeUpdate();
                s_logger.debug("Unique key did not exist on host_details -  added new one");
                pstmtUpdate.close();
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Failed to check/update the host_details unique key ", e);
        }
    }

};