//,temp,UsageNetworkDaoImpl.java,104,132,temp,UsageDaoImpl.java,177,216
//,3
public class xxx {
    @Override
    public void saveUserStats(List<UserStatisticsVO> userStats) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        try {
            txn.start();
            String sql = INSERT_USER_STATS;
            PreparedStatement pstmt = null;
            pstmt = txn.prepareAutoCloseStatement(sql); // in reality I just want CLOUD_USAGE dataSource connection
            for (UserStatisticsVO userStat : userStats) {
                pstmt.setLong(1, userStat.getId());
                pstmt.setLong(2, userStat.getDataCenterId());
                pstmt.setLong(3, userStat.getAccountId());
                pstmt.setString(4, userStat.getPublicIpAddress());
                if (userStat.getDeviceId() != null) {
                    pstmt.setLong(5, userStat.getDeviceId());
                } else {
                    pstmt.setNull(5, Types.BIGINT);
                }
                pstmt.setString(6, userStat.getDeviceType());
                if (userStat.getNetworkId() != null) {
                    pstmt.setLong(7, userStat.getNetworkId());
                } else {
                    pstmt.setNull(7, Types.BIGINT);
                }
                pstmt.setLong(8, userStat.getNetBytesReceived());
                pstmt.setLong(9, userStat.getNetBytesSent());
                pstmt.setLong(10, userStat.getCurrentBytesReceived());
                pstmt.setLong(11, userStat.getCurrentBytesSent());
                pstmt.setLong(12, userStat.getAggBytesReceived());
                pstmt.setLong(13, userStat.getAggBytesSent());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            txn.commit();
        } catch (Exception ex) {
            txn.rollback();
            s_logger.error("error saving user stats to cloud_usage db", ex);
            throw new CloudRuntimeException(ex.getMessage());
        }
    }

};