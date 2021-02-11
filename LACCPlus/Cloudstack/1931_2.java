//,temp,DatabaseConfig.java,761,784,temp,DatabaseConfig.java,580,608
//,3
public class xxx {
    @DB
    public void saveCluster() {
        String name = _currentObjectParams.get("name");
        long id = Long.parseLong(_currentObjectParams.get("id"));
        long dataCenterId = Long.parseLong(_currentObjectParams.get("zoneId"));
        long podId = Long.parseLong(_currentObjectParams.get("podId"));
        String hypervisor = _currentObjectParams.get("hypervisorType");
        String insertSql1 =
            "INSERT INTO `cluster` (`id`, `name`, `data_center_id` , `pod_id`, `hypervisor_type` , `cluster_type`, `allocation_state`) VALUES (?,?,?,?,?,?,?)";

        TransactionLegacy txn = TransactionLegacy.currentTxn();
        try {
            PreparedStatement stmt = txn.prepareAutoCloseStatement(insertSql1);
            stmt.setLong(1, id);
            stmt.setString(2, name);
            stmt.setLong(3, dataCenterId);
            stmt.setLong(4, podId);
            stmt.setString(5, hypervisor);
            stmt.setString(6, "CloudManaged");
            stmt.setString(7, "Enabled");
            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error creating cluster: " + ex.getMessage());
            s_logger.error("error creating cluster", ex);
            return;
        }

    }

};