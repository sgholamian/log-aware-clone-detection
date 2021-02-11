//,temp,DatabaseAccessObject.java,56,63,temp,Upgrade410to420.java,2408,2416
//,3
public class xxx {
    public void dropColumn(Connection conn, String tableName, String columnName) {
        try (PreparedStatement pstmt = conn.prepareStatement("ALTER TABLE " + tableName + " DROP COLUMN " + columnName);){
            pstmt.executeUpdate();
            s_logger.debug("Column " + columnName + " is dropped successfully from the table " + tableName);
        } catch (SQLException e) {
            s_logger.warn("Unable to drop column " + columnName + " due to exception", e);
        }
    }

};