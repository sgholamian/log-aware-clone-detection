//,temp,UsageVMInstanceDaoImpl.java,71,90,temp,UsageVPNUserDaoImpl.java,52,76
//,3
public class xxx {
    @Override
    public void update(UsageVPNUserVO usage) {
        TransactionLegacy txn = TransactionLegacy.open(TransactionLegacy.USAGE_DB);
        try {
            txn.start();
            if (usage.getDeleted() != null) {
                try(PreparedStatement pstmt = txn.prepareStatement(UPDATE_DELETED);) {
                    if (pstmt != null) {
                        pstmt.setString(1, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), usage.getDeleted()));
                        pstmt.setLong(2, usage.getAccountId());
                        pstmt.setLong(3, usage.getUserId());
                        pstmt.executeUpdate();
                    }
                }catch (SQLException e) {
                    throw new CloudException("Error updating UsageVPNUserVO"+e.getMessage(), e);
                }
            }
            txn.commit();
        } catch (Exception e) {
            txn.rollback();
            s_logger.error("Error updating UsageVPNUserVO:"+e.getMessage(), e);
        } finally {
            txn.close();
        }
    }

};