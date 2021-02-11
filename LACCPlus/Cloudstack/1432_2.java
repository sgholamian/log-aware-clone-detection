//,temp,UsageNetworkDaoImpl.java,50,85,temp,UsageVmDiskDaoImpl.java,51,89
//,3
public class xxx {
    @Override
    public Map<String, UsageVmDiskVO> getRecentVmDiskStats() {
        TransactionLegacy txn = TransactionLegacy.open(TransactionLegacy.USAGE_DB);
        String sql = SELECT_LATEST_STATS;
        PreparedStatement pstmt = null;
        try {
            pstmt = txn.prepareAutoCloseStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            Map<String, UsageVmDiskVO> returnMap = new HashMap<String, UsageVmDiskVO>();
            while (rs.next()) {
                long accountId = rs.getLong(1);
                long zoneId = rs.getLong(2);
                long vmId = rs.getLong(3);
                Long volumeId = rs.getLong(4);
                long ioRead = rs.getLong(5);
                long ioWrite = rs.getLong(6);
                long aggIORead = rs.getLong(7);
                long aggIOWrite = rs.getLong(8);
                long bytesRead = rs.getLong(9);
                long bytesWrite = rs.getLong(10);
                long aggBytesRead = rs.getLong(11);
                long aggBytesWrite = rs.getLong(12);
                long eventTimeMillis = rs.getLong(13);
                if (vmId != 0) {
                    returnMap.put(zoneId + "-" + accountId + "-Vm-" + vmId + "-Disk-" + volumeId, new UsageVmDiskVO(accountId, zoneId, vmId, volumeId, ioRead, ioWrite,
                        aggIORead, aggIOWrite, bytesRead, bytesWrite, aggBytesRead, aggBytesWrite, eventTimeMillis));
                } else {
                    returnMap.put(zoneId + "-" + accountId, new UsageVmDiskVO(accountId, zoneId, vmId, volumeId, ioRead, ioWrite, aggIORead, aggIOWrite, bytesRead,
                        bytesWrite, aggBytesRead, aggBytesWrite, eventTimeMillis));
                }
            }
            return returnMap;
        } catch (Exception ex) {
            s_logger.error("error getting recent usage disk stats", ex);
        } finally {
            txn.close();
        }
        return null;
    }

};