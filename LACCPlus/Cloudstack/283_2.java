//,temp,ConfigurationDaoImpl.java,153,168,temp,ConfigurationDaoImpl.java,138,151
//,3
public class xxx {
    @Override
    @Deprecated
    public boolean update(String name, String value) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        try (PreparedStatement stmt = txn.prepareStatement(UPDATE_CONFIGURATION_SQL);){
            stmt.setString(1, value);
            stmt.setString(2, name);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            s_logger.warn("Unable to update Configuration Value", e);
        }
        return false;
    }

};