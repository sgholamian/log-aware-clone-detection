//,temp,UsageVMInstanceDaoImpl.java,50,69,temp,UsageLoadBalancerPolicyDaoImpl.java,73,97
//,3
public class xxx {
    @Override
    public void update(UsageLoadBalancerPolicyVO usage) {
        TransactionLegacy txn = TransactionLegacy.open(TransactionLegacy.USAGE_DB);
        try {
            txn.start();
            if (usage.getDeleted() != null) {
                try(PreparedStatement pstmt = txn.prepareStatement(UPDATE_DELETED);) {
                    if (pstmt != null) {
                        pstmt.setString(1, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), usage.getDeleted()));
                        pstmt.setLong(2, usage.getAccountId());
                        pstmt.setLong(3, usage.getId());
                        pstmt.executeUpdate();
                    }
                }catch (SQLException e) {
                    throw new CloudException("Error updating UsageLoadBalancerPolicyVO:" + e.getMessage(), e);
                }
            }
            txn.commit();
        } catch (Exception e) {
            txn.rollback();
            s_logger.warn("Error updating UsageLoadBalancerPolicyVO"+e.getMessage(), e);
        } finally {
            txn.close();
        }
    }

};