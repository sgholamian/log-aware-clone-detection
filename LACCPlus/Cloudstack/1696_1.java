//,temp,Upgrade218to224DomainVlans.java,115,139,temp,Upgrade222to224.java,322,338
//,3
public class xxx {
    private void performDbCleanup(Connection conn) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT domain_id FROM account_vlan_map");
            try {
                pstmt.executeQuery();
            } catch (SQLException e) {
                s_logger.debug("Assuming that domain_id field doesn't exist in account_vlan_map table, no need to upgrade");
                return;
            }

            pstmt = conn.prepareStatement("ALTER TABLE `cloud`.`account_vlan_map` DROP FOREIGN KEY `fk_account_vlan_map__domain_id`");
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement("ALTER TABLE `cloud`.`account_vlan_map` DROP COLUMN `domain_id`");
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement("DELETE FROM `cloud`.`account_vlan_map` WHERE account_id IS NULL");
            pstmt.executeUpdate();

            pstmt.close();

        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to delete domain_id field from account_vlan_map table due to:", e);
        }
    }

};