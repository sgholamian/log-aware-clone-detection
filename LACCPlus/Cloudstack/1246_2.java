//,temp,Upgrade2214to30.java,382,419,temp,Upgrade430to440.java,170,201
//,3
public class xxx {
    private void moveCidrsToTheirOwnTable(Connection conn) {
        s_logger.debug("Moving network acl item cidrs to a row per cidr");

        String networkAclItemSql = "SELECT id, cidr FROM `cloud`.`network_acl_item`";
        String networkAclItemCidrSql = "INSERT INTO `cloud`.`network_acl_item_cidrs` (network_acl_item_id, cidr) VALUES (?,?)";

        try (PreparedStatement pstmtItem = conn.prepareStatement(networkAclItemSql);
             ResultSet rsItems = pstmtItem.executeQuery();
             PreparedStatement pstmtCidr = conn.prepareStatement(networkAclItemCidrSql);
            ) {


            // for each network acl item
            while(rsItems.next()) {
                long itemId = rsItems.getLong(1);
                // get the source cidr list
                String cidrList = rsItems.getString(2);
                s_logger.debug("Moving '" + cidrList +  "' to a row per cidr");
                // split it
                String[] cidrArray = cidrList.split(",");
                // insert a record per cidr
                pstmtCidr.setLong(1, itemId);
                for (String cidr : cidrArray) {
                    pstmtCidr.setString(2, cidr);
                    pstmtCidr.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Exception while Moving network acl item cidrs to a row per cidr", e);
        }
        s_logger.debug("Done moving network acl item cidrs to a row per cidr");
    }

};