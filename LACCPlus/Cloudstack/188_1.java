//,temp,DatabaseAccessObject.java,65,74,temp,DatabaseAccessObject.java,47,54
//,3
public class xxx {
    public boolean columnExists(Connection conn, String tableName, String columnName) {
        boolean columnExists = false;
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT " + columnName + " FROM " + tableName);){
            pstmt.executeQuery();
            columnExists = true;
        } catch (SQLException e) {
            s_logger.debug("Field " + columnName + " doesn't exist in " + tableName + " ignoring exception: " + e.getMessage());
        }
        return columnExists;
    }

};