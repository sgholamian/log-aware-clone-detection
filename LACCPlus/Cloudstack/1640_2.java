//,temp,CapacityDaoImpl.java,543,564,temp,TemplateDataStoreDaoImpl.java,536,552
//,3
public class xxx {
    @Override
    public void expireDnldUrlsForZone(Long dcId){
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        PreparedStatement pstmt = null;
        try {
            txn.start();
            pstmt = txn.prepareAutoCloseStatement(EXPIRE_DOWNLOAD_URLS_FOR_ZONE);
            pstmt.setDate(1, new java.sql.Date(-1l));// Set the time before the epoch time.
            pstmt.setLong(2, dcId);
            pstmt.executeUpdate();
            txn.commit();
        } catch (Exception e) {
            txn.rollback();
            s_logger.warn("Failed expiring download urls for dcId: " + dcId, e);
        }

    }

};