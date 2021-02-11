//,temp,UsageVolumeDaoImpl.java,78,98,temp,UsageStorageDaoImpl.java,117,144
//,3
public class xxx {
    @Override
    public void update(UsageStorageVO usage) {
        TransactionLegacy txn = TransactionLegacy.open(TransactionLegacy.USAGE_DB);
        try {
            txn.start();
            if (usage.getDeleted() != null) {
                try(PreparedStatement pstmt = txn.prepareStatement(UPDATE_DELETED);) {
                    if (pstmt != null) {
                        pstmt.setString(1, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), usage.getDeleted()));
                        pstmt.setLong(2, usage.getAccountId());
                        pstmt.setLong(3, usage.getId());
                        pstmt.setInt(4, usage.getStorageType());
                        pstmt.setLong(5, usage.getZoneId());
                        pstmt.executeUpdate();
                    }
                }catch (SQLException e)
                {
                    throw new CloudException("UsageStorageVO update Error:"+e.getMessage(),e);
                }
            }
            txn.commit();
        } catch (Exception e) {
            txn.rollback();
            s_logger.error("Error updating UsageStorageVO:"+e.getMessage(), e);
        } finally {
            txn.close();
        }
    }

};