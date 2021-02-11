//,temp,ConfigurationDaoImpl.java,153,168,temp,ConfigurationDaoImpl.java,138,151
//,3
public class xxx {
    @Override
    public boolean update(String name, String category, String value) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        try {
            value = ("Hidden".equals(category) || "Secure".equals(category)) ? DBEncryptionUtil.encrypt(value) : value;
            try (PreparedStatement stmt = txn.prepareStatement(UPDATE_CONFIGURATION_SQL);) {
                stmt.setString(1, value);
                stmt.setString(2, name);
                stmt.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            s_logger.warn("Unable to update Configuration Value", e);
        }
        return false;
    }

};