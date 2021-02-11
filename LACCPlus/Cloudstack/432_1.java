//,temp,UsageNetworkOfferingDaoImpl.java,56,81,temp,UsageVolumeDaoImpl.java,55,76
//,3
public class xxx {
    @Override
    public void update(UsageNetworkOfferingVO usage) {
        TransactionLegacy txn = TransactionLegacy.open(TransactionLegacy.USAGE_DB);
        try {
            txn.start();
            if (usage.getDeleted() != null) {
                try(PreparedStatement pstmt = txn.prepareStatement(UPDATE_DELETED);) {
                    if (pstmt != null) {
                        pstmt.setString(1, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), usage.getDeleted()));
                        pstmt.setLong(2, usage.getAccountId());
                        pstmt.setLong(3, usage.getVmInstanceId());
                        pstmt.setLong(4, usage.getNetworkOfferingId());
                        pstmt.executeUpdate();
                    }
                  }catch (SQLException e) {
                    throw new CloudException("Error updating UsageNetworkOfferingVO:"+e.getMessage(), e);
                }
            }
            txn.commit();
        } catch (Exception e) {
            txn.rollback();
            s_logger.warn("Error updating UsageNetworkOfferingVO:"+e.getMessage(), e);
        } finally {
            txn.close();
        }
    }

};