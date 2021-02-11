//,temp,UsageDaoImpl.java,349,393,temp,UsageDaoImpl.java,315,347
//,3
public class xxx {
    @Override
    public void updateVmDiskStats(List<VmDiskStatisticsVO> vmDiskStats) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        try {
            txn.start();
            String sql = UPDATE_VM_DISK_STATS;
            PreparedStatement pstmt = null;
            pstmt = txn.prepareAutoCloseStatement(sql); // in reality I just want CLOUD_USAGE dataSource connection
            for (VmDiskStatisticsVO vmDiskStat : vmDiskStats) {
                pstmt.setLong(1, vmDiskStat.getNetIORead());
                pstmt.setLong(2, vmDiskStat.getNetIOWrite());
                pstmt.setLong(3, vmDiskStat.getCurrentIORead());
                pstmt.setLong(4, vmDiskStat.getCurrentIOWrite());
                pstmt.setLong(5, vmDiskStat.getAggIORead());
                pstmt.setLong(6, vmDiskStat.getAggIOWrite());
                pstmt.setLong(7, vmDiskStat.getNetBytesRead());
                pstmt.setLong(8, vmDiskStat.getNetBytesWrite());
                pstmt.setLong(9, vmDiskStat.getCurrentBytesRead());
                pstmt.setLong(10, vmDiskStat.getCurrentBytesWrite());
                pstmt.setLong(11, vmDiskStat.getAggBytesRead());
                pstmt.setLong(12, vmDiskStat.getAggBytesWrite());
                pstmt.setLong(13, vmDiskStat.getId());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            txn.commit();
        } catch (Exception ex) {
            txn.rollback();
            s_logger.error("error updating vm disk stats to cloud_usage db", ex);
            throw new CloudRuntimeException(ex.getMessage());
        }

    }

};