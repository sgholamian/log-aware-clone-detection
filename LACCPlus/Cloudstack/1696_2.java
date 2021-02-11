//,temp,Upgrade218to224DomainVlans.java,115,139,temp,Upgrade222to224.java,322,338
//,3
public class xxx {
    private void dropIndexIfExists(Connection conn) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("SHOW INDEX FROM domain WHERE KEY_NAME = 'path'");
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                pstmt = conn.prepareStatement("ALTER TABLE `cloud`.`domain` DROP INDEX `path`");
                pstmt.executeUpdate();
                s_logger.debug("Unique key 'path' is removed successfully");
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to drop 'path' index for 'domain' table due to:", e);
        }
    }

};