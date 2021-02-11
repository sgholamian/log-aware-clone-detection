//,temp,UsageNetworkDaoImpl.java,104,132,temp,UsageVmDiskDaoImpl.java,108,139
//,3
public class xxx {
    @Override
    public void saveUsageVmDisks(List<UsageVmDiskVO> usageVmDisks) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        try {
            txn.start();
            String sql = INSERT_USAGE_VM_DISK;
            PreparedStatement pstmt = null;
            pstmt = txn.prepareAutoCloseStatement(sql); // in reality I just want CLOUD_USAGE dataSource connection
            for (UsageVmDiskVO usageVmDisk : usageVmDisks) {
                pstmt.setLong(1, usageVmDisk.getAccountId());
                pstmt.setLong(2, usageVmDisk.getZoneId());
                pstmt.setLong(3, usageVmDisk.getVmId());
                pstmt.setLong(4, usageVmDisk.getVolumeId());
                pstmt.setLong(5, usageVmDisk.getIORead());
                pstmt.setLong(6, usageVmDisk.getIOWrite());
                pstmt.setLong(7, usageVmDisk.getAggIORead());
                pstmt.setLong(8, usageVmDisk.getAggIOWrite());
                pstmt.setLong(9, usageVmDisk.getBytesRead());
                pstmt.setLong(10, usageVmDisk.getBytesWrite());
                pstmt.setLong(11, usageVmDisk.getAggBytesRead());
                pstmt.setLong(12, usageVmDisk.getAggBytesWrite());
                pstmt.setLong(13, usageVmDisk.getEventTimeMillis());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            txn.commit();
        } catch (Exception ex) {
            txn.rollback();
            s_logger.error("error saving usage_vm_disk to cloud_usage db", ex);
            throw new CloudRuntimeException(ex.getMessage());
        }
    }

};