//,temp,LaunchPermissionDaoImpl.java,66,86,temp,CapacityDaoImpl.java,543,564
//,3
public class xxx {
    @Override
    public void removePermissions(long templateId, List<Long> accountIds) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        PreparedStatement pstmt = null;
        try {
            txn.start();
            String sql = REMOVE_LAUNCH_PERMISSION;
            pstmt = txn.prepareAutoCloseStatement(sql);
            for (Long accountId : accountIds) {
                pstmt.setLong(1, templateId);
                pstmt.setLong(2, accountId.longValue());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            txn.commit();
        } catch (Exception e) {
            txn.rollback();
            s_logger.warn("Error removing launch permissions", e);
            throw new CloudRuntimeException("Error removing launch permissions", e);
        }
    }

};