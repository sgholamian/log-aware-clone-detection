//,temp,Upgrade222to224.java,434,450,temp,Upgrade222to224.java,322,338
//,3
public class xxx {
    private void upgradeGuestOs(Connection conn) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * from guest_os WHERE id=138");
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                pstmt = conn.prepareStatement("INSERT INTO `cloud`.`guest_os` (id, category_id, display_name) VALUES (138, 7, 'None')");
                pstmt.executeUpdate();
                s_logger.debug("Inserted NONE category to guest_os table");
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unalbe to insert NONE guest category to guest_os table due to:", e);
        }
    }

};