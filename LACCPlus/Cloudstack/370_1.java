//,temp,UsageVolumeDaoImpl.java,78,98,temp,UsageStorageDaoImpl.java,117,144
//,3
public class xxx {
    @Override
    public void update(UsageVolumeVO usage) {
        TransactionLegacy txn = TransactionLegacy.open(TransactionLegacy.USAGE_DB);
        PreparedStatement pstmt = null;
        try {
            txn.start();
            if (usage.getDeleted() != null) {
                pstmt = txn.prepareAutoCloseStatement(UPDATE_DELETED);
                pstmt.setString(1, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), usage.getDeleted()));
                pstmt.setLong(2, usage.getAccountId());
                pstmt.setLong(3, usage.getId());
                pstmt.executeUpdate();
            }
            txn.commit();
        } catch (Exception e) {
            txn.rollback();
            s_logger.warn("Error updating UsageVolumeVO", e);
        } finally {
            txn.close();
        }
    }

};