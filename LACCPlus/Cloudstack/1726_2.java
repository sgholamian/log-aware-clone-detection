//,temp,DatabaseAccessObject.java,47,54,temp,Upgrade410to420.java,2246,2260
//,3
public class xxx {
    private void fixNiciraKeys(Connection conn) {
        //First drop the key if it exists.
        List<String> keys = new ArrayList<String>();
        s_logger.debug("Dropping foreign key fk_nicira_nvp_nic_map__nic from the table nicira_nvp_nic_map if it exists");
        keys.add("fk_nicira_nvp_nic_map__nic");
        DbUpgradeUtils.dropKeysIfExist(conn, "nicira_nvp_nic_map", keys, true);
        //Now add foreign key.
        try(PreparedStatement pstmt = conn.prepareStatement("ALTER TABLE `cloud`.`nicira_nvp_nic_map` ADD CONSTRAINT `fk_nicira_nvp_nic_map__nic` FOREIGN KEY (`nic`) REFERENCES `nics` (`uuid`) ON DELETE CASCADE");)
        {
            pstmt.executeUpdate();
            s_logger.debug("Added foreign key fk_nicira_nvp_nic_map__nic to the table nicira_nvp_nic_map");
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to add foreign key fk_nicira_nvp_nic_map__nic to the table nicira_nvp_nic_map", e);
        }
    }

};