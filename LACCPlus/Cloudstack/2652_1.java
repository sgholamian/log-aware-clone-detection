//,temp,UsageNetworkDaoImpl.java,104,132,temp,UsageVmDiskDaoImpl.java,108,139
//,3
public class xxx {
    @Override
    public void saveUsageNetworks(List<UsageNetworkVO> usageNetworks) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        try {
            txn.start();
            String sql = INSERT_USAGE_NETWORK;
            PreparedStatement pstmt = null;
            pstmt = txn.prepareAutoCloseStatement(sql); // in reality I just want CLOUD_USAGE dataSource connection
            for (UsageNetworkVO usageNetwork : usageNetworks) {
                pstmt.setLong(1, usageNetwork.getAccountId());
                pstmt.setLong(2, usageNetwork.getZoneId());
                pstmt.setLong(3, usageNetwork.getHostId());
                pstmt.setString(4, usageNetwork.getHostType());
                pstmt.setLong(5, usageNetwork.getNetworkId());
                pstmt.setLong(6, usageNetwork.getBytesSent());
                pstmt.setLong(7, usageNetwork.getBytesReceived());
                pstmt.setLong(8, usageNetwork.getAggBytesReceived());
                pstmt.setLong(9, usageNetwork.getAggBytesSent());
                pstmt.setLong(10, usageNetwork.getEventTimeMillis());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            txn.commit();
        } catch (Exception ex) {
            txn.rollback();
            s_logger.error("error saving usage_network to cloud_usage db", ex);
            throw new CloudRuntimeException(ex.getMessage());
        }
    }

};