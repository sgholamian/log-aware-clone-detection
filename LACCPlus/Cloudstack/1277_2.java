//,temp,UsageNetworkDaoImpl.java,104,132,temp,UsageDaoImpl.java,218,243
//,3
public class xxx {
    @Override
    public void updateUserStats(List<UserStatisticsVO> userStats) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        try {
            txn.start();
            String sql = UPDATE_USER_STATS;
            PreparedStatement pstmt = null;
            pstmt = txn.prepareAutoCloseStatement(sql); // in reality I just want CLOUD_USAGE dataSource connection
            for (UserStatisticsVO userStat : userStats) {
                pstmt.setLong(1, userStat.getNetBytesReceived());
                pstmt.setLong(2, userStat.getNetBytesSent());
                pstmt.setLong(3, userStat.getCurrentBytesReceived());
                pstmt.setLong(4, userStat.getCurrentBytesSent());
                pstmt.setLong(5, userStat.getAggBytesReceived());
                pstmt.setLong(6, userStat.getAggBytesSent());
                pstmt.setLong(7, userStat.getId());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            txn.commit();
        } catch (Exception ex) {
            txn.rollback();
            s_logger.error("error updating user stats to cloud_usage db", ex);
            throw new CloudRuntimeException(ex.getMessage());
        }
    }

};