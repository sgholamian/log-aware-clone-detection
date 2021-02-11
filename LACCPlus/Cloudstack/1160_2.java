//,temp,Upgrade218to22.java,2297,2326,temp,Upgrade218to22.java,2261,2293
//,3
public class xxx {
    private void modifyIndexes(Connection conn) {
        try (
            // removed indexes
                PreparedStatement show__Index = conn.prepareStatement("SHOW INDEX FROM security_group WHERE KEY_NAME = 'fk_network_group__account_id'");
                ResultSet result__index = show__Index.executeQuery();
            ) {
            if (result__index.next()) {
                try (PreparedStatement alterTable = conn.prepareStatement("ALTER TABLE `cloud`.`security_group` DROP INDEX `fk_network_group__account_id`");) {
                    alterTable.executeUpdate();
                    s_logger.debug("Unique key 'fk_network_group__account_id' is removed successfully");
                }
            }

            try (
                    PreparedStatement show___Index = conn.prepareStatement("SHOW INDEX FROM security_group WHERE KEY_NAME = 'fk_network_group___account_id'");
                    ResultSet result___index = show___Index.executeQuery();
                    ) {
                if (result___index.next()) {
                    try (PreparedStatement pstmt = conn.prepareStatement("ALTER TABLE `cloud`.`security_group` DROP INDEX `fk_network_group___account_id`");) {
                        pstmt.executeUpdate();
                        s_logger.debug("Unique key 'fk_network_group___account_id' is removed successfully");
                    }
                }
            }
            // add indexes
            try (PreparedStatement add_index =
                conn.prepareStatement("ALTER TABLE `cloud`.`security_group` ADD CONSTRAINT `fk_security_group___account_id` FOREIGN KEY `fk_security_group__account_id` (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE");) {
                add_index.executeUpdate();
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to drop indexes for 'security_group' table due to:", e);
        }
    }

};