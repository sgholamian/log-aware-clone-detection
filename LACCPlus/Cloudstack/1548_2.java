//,temp,HAConfigDaoImpl.java,136,146,temp,ManagementServerHostDaoImpl.java,168,188
//,3
public class xxx {
    @Override
    @DB
    public int increaseAlertCount(long id) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        PreparedStatement pstmt = null;
        int changedRows = 0;
        try {
            txn.start();

            pstmt = txn.prepareAutoCloseStatement("update mshost set alert_count=alert_count+1 where id=? and alert_count=0");
            pstmt.setLong(1, id);

            changedRows = pstmt.executeUpdate();
            txn.commit();
        } catch (Exception e) {
            s_logger.warn("Unexpected exception, ", e);
            throw new RuntimeException(e.getMessage(), e);
        }

        return changedRows;
    }

};