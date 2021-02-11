//,temp,UsageNetworkDaoImpl.java,50,85,temp,UsageVmDiskDaoImpl.java,51,89
//,3
public class xxx {
    @Override
    public Map<String, UsageNetworkVO> getRecentNetworkStats() {
        TransactionLegacy txn = TransactionLegacy.open(TransactionLegacy.USAGE_DB);
        String sql = SELECT_LATEST_STATS;
        PreparedStatement pstmt = null;
        try {
            pstmt = txn.prepareAutoCloseStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            Map<String, UsageNetworkVO> returnMap = new HashMap<String, UsageNetworkVO>();
            while (rs.next()) {
                long accountId = rs.getLong(1);
                long zoneId = rs.getLong(2);
                Long hostId = rs.getLong(3);
                String hostType = rs.getString(4);
                Long networkId = rs.getLong(5);
                long bytesSent = rs.getLong(6);
                long bytesReceived = rs.getLong(7);
                long aggBytesReceived = rs.getLong(8);
                long aggBytesSent = rs.getLong(9);
                long eventTimeMillis = rs.getLong(10);
                if (hostId != 0) {
                    returnMap.put(zoneId + "-" + accountId + "-Host-" + hostId, new UsageNetworkVO(accountId, zoneId, hostId, hostType, networkId, bytesSent,
                        bytesReceived, aggBytesReceived, aggBytesSent, eventTimeMillis));
                } else {
                    returnMap.put(zoneId + "-" + accountId, new UsageNetworkVO(accountId, zoneId, hostId, hostType, networkId, bytesSent, bytesReceived,
                        aggBytesReceived, aggBytesSent, eventTimeMillis));
                }
            }
            return returnMap;
        } catch (Exception ex) {
            s_logger.error("error getting recent usage network stats", ex);
        } finally {
            txn.close();
        }
        return null;
    }

};