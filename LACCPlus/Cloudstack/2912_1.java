//,temp,UsageSecurityGroupDaoImpl.java,56,81,temp,UsageNetworkOfferingDaoImpl.java,56,81
//,2
public class xxx {
    @Override
    public void update(UsageSecurityGroupVO usage) {
        TransactionLegacy txn = TransactionLegacy.open(TransactionLegacy.USAGE_DB);
        try {
            txn.start();
            if (usage.getDeleted() != null) {
                try(PreparedStatement pstmt = txn.prepareStatement(UPDATE_DELETED);) {
                    if (pstmt != null) {
                        pstmt.setString(1, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), usage.getDeleted()));
                        pstmt.setLong(2, usage.getAccountId());
                        pstmt.setLong(3, usage.getVmInstanceId());
                        pstmt.setLong(4, usage.getSecurityGroupId());
                        pstmt.executeUpdate();
                    }
                }catch (SQLException e) {
                    throw new CloudException("Error updating UsageSecurityGroupVO:"+e.getMessage(), e);
                }
            }
            txn.commit();
        } catch (Exception e) {
            txn.rollback();
            s_logger.warn("Error updating UsageSecurityGroupVO:"+e.getMessage(), e);
        } finally {
            txn.close();
        }
    }

};