//,temp,VmDiskStatisticsDaoImpl.java,112,128,temp,UsageJobDaoImpl.java,42,59
//,3
public class xxx {
    @Override
    public List<VmDiskStatisticsVO> listUpdatedStats() {
        List<VmDiskStatisticsVO> vmDiskStats = new ArrayList<VmDiskStatisticsVO>();

        TransactionLegacy txn = TransactionLegacy.currentTxn();
        try {
            PreparedStatement pstmt = null;
            pstmt = txn.prepareAutoCloseStatement(UPDATED_VM_NETWORK_STATS_SEARCH);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                vmDiskStats.add(toEntityBean(rs, false));
            }
        } catch (Exception ex) {
            s_logger.error("error lisitng updated vm disk stats", ex);
        }
        return vmDiskStats;
    }

};