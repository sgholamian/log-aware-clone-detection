//,temp,Upgrade410to420.java,2262,2277,temp,Upgrade305to306.java,116,132
//,2
public class xxx {
    private void fixRouterKeys(Connection conn) {
        //First drop the key if it exists.
        List<String> keys = new ArrayList<String>();
        s_logger.debug("Dropping foreign key fk_router_network_ref__router_id from the table router_network_ref if it exists");
        keys.add("fk_router_network_ref__router_id");
        DbUpgradeUtils.dropKeysIfExist(conn, "router_network_ref", keys, true);
        //Now add foreign key.
        try (PreparedStatement pstmt =
                     conn.prepareStatement("ALTER TABLE `cloud`.`router_network_ref` ADD CONSTRAINT `fk_router_network_ref__router_id` FOREIGN KEY (`router_id`) REFERENCES `domain_router` (`id`) ON DELETE CASCADE");)
        {
            pstmt.executeUpdate();
            s_logger.debug("Added foreign key fk_router_network_ref__router_id to the table router_network_ref");
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to add foreign key fk_router_network_ref__router_id to the table router_network_ref", e);
        }
    }

};