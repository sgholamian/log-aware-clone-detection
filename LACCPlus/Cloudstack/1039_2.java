//,temp,VmDiskStatisticsDaoImpl.java,112,128,temp,UsageDaoImpl.java,298,313
//,3
public class xxx {
    @Override
    public Long getLastVmDiskStatsId() {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        PreparedStatement pstmt = null;
        String sql = GET_LAST_VM_DISK_STATS;
        try {
            pstmt = txn.prepareAutoCloseStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Long.valueOf(rs.getLong(1));
            }
        } catch (Exception ex) {
            s_logger.error("error getting last vm disk stats id", ex);
        }
        return null;
    }

};