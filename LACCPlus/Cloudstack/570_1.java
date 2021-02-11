//,temp,UsageDaoImpl.java,349,393,temp,UsageDaoImpl.java,177,216
//,3
public class xxx {
    @Override
    public void saveVmDiskStats(List<VmDiskStatisticsVO> vmDiskStats) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        try {
            txn.start();
            String sql = INSERT_VM_DISK_STATS;
            PreparedStatement pstmt = null;
            pstmt = txn.prepareAutoCloseStatement(sql); // in reality I just want CLOUD_USAGE dataSource connection
            for (VmDiskStatisticsVO vmDiskStat : vmDiskStats) {
                pstmt.setLong(1, vmDiskStat.getId());
                pstmt.setLong(2, vmDiskStat.getDataCenterId());
                pstmt.setLong(3, vmDiskStat.getAccountId());
                if (vmDiskStat.getVmId() != null) {
                    pstmt.setLong(4, vmDiskStat.getVmId());
                } else {
                    pstmt.setNull(4, Types.BIGINT);
                }
                if (vmDiskStat.getVolumeId() != null) {
                    pstmt.setLong(5, vmDiskStat.getVolumeId());
                } else {
                    pstmt.setNull(5, Types.BIGINT);
                }
                pstmt.setLong(6, vmDiskStat.getNetIORead());
                pstmt.setLong(7, vmDiskStat.getNetIOWrite());
                pstmt.setLong(8, vmDiskStat.getCurrentIORead());
                pstmt.setLong(9, vmDiskStat.getCurrentIOWrite());
                pstmt.setLong(10, vmDiskStat.getAggIORead());
                pstmt.setLong(11, vmDiskStat.getAggIOWrite());
                pstmt.setLong(12, vmDiskStat.getNetBytesRead());
                pstmt.setLong(13, vmDiskStat.getNetBytesWrite());
                pstmt.setLong(14, vmDiskStat.getCurrentBytesRead());
                pstmt.setLong(15, vmDiskStat.getCurrentBytesWrite());
                pstmt.setLong(16, vmDiskStat.getAggBytesRead());
                pstmt.setLong(17, vmDiskStat.getAggBytesWrite());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            txn.commit();
        } catch (Exception ex) {
            txn.rollback();
            s_logger.error("error saving vm disk stats to cloud_usage db", ex);
            throw new CloudRuntimeException(ex.getMessage());
        }

    }

};