//,temp,DatabaseAccessObject.java,56,63,temp,DatabaseAccessObject.java,29,45
//,3
public class xxx {
    public void dropKey(Connection conn, String tableName, String key, boolean isForeignKey)
    {
        String alter_sql_str;
        if (isForeignKey) {
            alter_sql_str = "ALTER TABLE " + tableName + " DROP FOREIGN KEY " + key;
        } else {
            alter_sql_str = "ALTER TABLE " + tableName + " DROP KEY " + key;
        }
        try(PreparedStatement pstmt = conn.prepareStatement(alter_sql_str);)
        {
            pstmt.executeUpdate();
            s_logger.debug("Key " + key + " is dropped successfully from the table " + tableName);
        } catch (SQLException e) {
            s_logger.debug("Ignored SQL Exception when trying to drop " + (isForeignKey ? "foreign " : "") + "key " + key + " on table "  + tableName + " exception: " + e.getMessage());

        }
    }

};