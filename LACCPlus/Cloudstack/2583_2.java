//,temp,UsageLoadBalancerPolicyDaoImpl.java,73,97,temp,UsageVolumeDaoImpl.java,55,76
//,3
public class xxx {
    @Override
    public void removeBy(long accountId, long volId) {
        TransactionLegacy txn = TransactionLegacy.open(TransactionLegacy.USAGE_DB);
        try {
            txn.start();
            try(PreparedStatement pstmt = txn.prepareStatement(REMOVE_BY_USERID_VOLID);) {
                if (pstmt != null) {
                    pstmt.setLong(1, accountId);
                    pstmt.setLong(2, volId);
                    pstmt.executeUpdate();
                }
            }catch (SQLException e) {
                throw new CloudException("Error removing usageVolumeVO:"+e.getMessage(), e);
            }
            txn.commit();
        } catch (Exception e) {
            txn.rollback();
            s_logger.warn("Error removing usageVolumeVO:"+e.getMessage(), e);
        } finally {
            txn.close();
        }
    }

};