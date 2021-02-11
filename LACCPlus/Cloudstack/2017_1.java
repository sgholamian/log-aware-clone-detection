//,temp,UsageVMInstanceDaoImpl.java,50,69,temp,UsageIPAddressDaoImpl.java,57,82
//,3
public class xxx {
    @Override
    public void update(UsageVMInstanceVO instance) {
        TransactionLegacy txn = TransactionLegacy.open(TransactionLegacy.USAGE_DB);
        PreparedStatement pstmt = null;
        try {
            txn.start();
            String sql = UPDATE_USAGE_INSTANCE_SQL;
            pstmt = txn.prepareAutoCloseStatement(sql);
            pstmt.setString(1, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), instance.getEndDate()));
            pstmt.setLong(2, instance.getAccountId());
            pstmt.setLong(3, instance.getVmInstanceId());
            pstmt.setInt(4, instance.getUsageType());
            pstmt.executeUpdate();
            txn.commit();
        } catch (Exception e) {
            s_logger.warn(e);
        } finally {
            txn.close();
        }
    }

};