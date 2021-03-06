//,temp,DatabaseAccessObject.java,47,54,temp,Upgrade410to420.java,2408,2416
//,3
public class xxx {
    public void dropPrimaryKey(Connection conn, String tableName) {
        try(PreparedStatement pstmt = conn.prepareStatement("ALTER TABLE " + tableName + " DROP PRIMARY KEY ");) {
            pstmt.executeUpdate();
            s_logger.debug("Primary key is dropped successfully from the table " + tableName);
        } catch (SQLException e) {
            s_logger.debug("Ignored SQL Exception when trying to drop primary key on table " + tableName + " exception: " + e.getMessage());
        }
    }

};