//,temp,DatabaseAccessObject.java,56,63,temp,Upgrade410to420.java,270,280
//,3
public class xxx {
    private void setConfigurationParameter(Connection conn, String category, String paramName, String paramVal) {
        try (PreparedStatement pstmt = conn.prepareStatement("UPDATE `cloud`.`configuration` SET value = ? WHERE name = ?;");)
        {
            pstmt.setString(1, paramVal);
            pstmt.setString(2, paramName);
            s_logger.debug("Updating global configuration parameter " + paramName + " with value " + paramVal + ". Update SQL statement is " + pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to set global configuration parameter " + paramName + " to " + paramVal + ". ", e);
        }
    }

};