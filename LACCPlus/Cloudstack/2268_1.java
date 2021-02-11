//,temp,Upgrade222to224.java,62,75,temp,Upgrade224to225.java,339,353
//,3
public class xxx {
    private void fixRelatedFkeyOnNetworksTable(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("ALTER TABLE `cloud`.`networks` DROP FOREIGN KEY `fk_networks__related`");
        try {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            s_logger.debug("Ignore if the key is not there.");
        }
        pstmt.close();

        pstmt =
            conn.prepareStatement("ALTER TABLE `cloud`.`networks` ADD CONSTRAINT `fk_networks__related` FOREIGN KEY(`related`) REFERENCES `networks`(`id`) ON DELETE CASCADE");
        pstmt.executeUpdate();
        pstmt.close();
    }

};