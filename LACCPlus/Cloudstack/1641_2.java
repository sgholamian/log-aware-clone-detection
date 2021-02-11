//,temp,CapacityDaoImpl.java,543,564,temp,ManagementServerHostDaoImpl.java,126,150
//,3
public class xxx {
    @Override
    @DB
    public void update(long id, long runid, Date lastUpdate) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        PreparedStatement pstmt = null;
        try {
            txn.start();

            pstmt = txn.prepareAutoCloseStatement("update mshost set last_update=?, removed=null, alert_count=0 where id=? and runid=?");
            pstmt.setString(1, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), lastUpdate));
            pstmt.setLong(2, id);
            pstmt.setLong(3, runid);

            int count = pstmt.executeUpdate();
            txn.commit();

            if (count < 1) {
                s_logger.info("Invalid cluster session detected, runId " + runid + " is no longer valid");
                throw new CloudRuntimeException("Invalid cluster session detected, runId " + runid + " is no longer valid", new ClusterInvalidSessionException("runId " + runid + " is no longer valid"));
            }
        } catch (Exception e) {
            s_logger.warn("Unexpected exception, ", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

};