//,temp,Upgrade41120to41200.java,63,69,temp,Upgrade410to420.java,2408,2416
//,3
public class xxx {
    private void updateManagementServerHostUuid(Connection conn) {
        try (final PreparedStatement updateStatement = conn.prepareStatement("UPDATE cloud.mshost SET uuid=UUID()")) {
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Failed to add an UUID to each management server.", e);
        }
    }

};