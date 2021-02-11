//,temp,UsageVMInstanceDaoImpl.java,71,90,temp,UsageLoadBalancerPolicyDaoImpl.java,53,71
//,3
public class xxx {
    @Override
    public void delete(UsageVMInstanceVO instance) {
        TransactionLegacy txn = TransactionLegacy.open(TransactionLegacy.USAGE_DB);
        PreparedStatement pstmt = null;
        try {
            txn.start();
            String sql = DELETE_USAGE_INSTANCE_SQL;
            pstmt = txn.prepareAutoCloseStatement(sql);
            pstmt.setLong(1, instance.getAccountId());
            pstmt.setLong(2, instance.getVmInstanceId());
            pstmt.setInt(3, instance.getUsageType());
            pstmt.executeUpdate();
            txn.commit();
        } catch (Exception ex) {
            txn.rollback();
            s_logger.error("error deleting usage vm instance with vmId: " + instance.getVmInstanceId() + ", for account with id: " + instance.getAccountId());
        } finally {
            txn.close();
        }
    }

};