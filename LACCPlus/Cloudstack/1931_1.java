//,temp,DatabaseConfig.java,761,784,temp,DatabaseConfig.java,580,608
//,3
public class xxx {
    private void saveVirtualRouterProvider() {
        long id = Long.parseLong(_currentObjectParams.get("id"));
        long nspId = Long.parseLong(_currentObjectParams.get("nspId"));
        String uuid = UUID.randomUUID().toString();
        String type = _currentObjectParams.get("type");

        String insertSql1 = "INSERT INTO `virtual_router_providers` (`id`, `nsp_id`, `uuid` , `type` , `enabled`) " + "VALUES (?,?,?,?,?)";

        TransactionLegacy txn = TransactionLegacy.currentTxn();
        try {
            PreparedStatement stmt = txn.prepareAutoCloseStatement(insertSql1);
            stmt.setLong(1, id);
            stmt.setLong(2, nspId);
            stmt.setString(3, uuid);
            stmt.setString(4, type);
            stmt.setInt(5, 1);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error creating virtual router provider: " + ex.getMessage());
            s_logger.error("error creating virtual router provider ", ex);
            return;
        }

    }

};