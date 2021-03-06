//,temp,UsageStorageDaoImpl.java,93,115,temp,UsageVPNUserDaoImpl.java,52,76
//,3
public class xxx {
    @Override
    public void removeBy(long accountId, long volId, int storageType) {
        TransactionLegacy txn = TransactionLegacy.open(TransactionLegacy.USAGE_DB);
        try {
            txn.start();
            String sql = REMOVE_BY_USERID_STORAGEID;
            try( PreparedStatement pstmt = txn.prepareStatement(sql);) {
                pstmt.setLong(1, accountId);
                pstmt.setLong(2, volId);
                pstmt.setInt(3, storageType);
                pstmt.executeUpdate();
            }catch(SQLException e)
            {
                throw new CloudException("removeBy:Exception:"+e.getMessage(),e);
            }
            txn.commit();
        } catch (Exception e) {
            txn.rollback();
            s_logger.error("Error removing usageStorageVO", e);
        } finally {
            txn.close();
        }
    }

};