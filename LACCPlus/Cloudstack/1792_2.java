//,temp,UsageEventDaoImpl.java,193,211,temp,VmDiskStatisticsDaoImpl.java,90,110
//,3
public class xxx {
    @Override
    public List<VmDiskStatisticsVO> listActiveAndRecentlyDeleted(Date minRemovedDate, int startIndex, int limit) {
        List<VmDiskStatisticsVO> vmDiskStats = new ArrayList<VmDiskStatisticsVO>();
        if (minRemovedDate == null)
            return vmDiskStats;

        TransactionLegacy txn = TransactionLegacy.currentTxn();
        try {
            String sql = ACTIVE_AND_RECENTLY_DELETED_SEARCH + " LIMIT " + startIndex + "," + limit;
            PreparedStatement pstmt = null;
            pstmt = txn.prepareAutoCloseStatement(sql);
            pstmt.setString(1, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), minRemovedDate));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                vmDiskStats.add(toEntityBean(rs, false));
            }
        } catch (Exception ex) {
            s_logger.error("error saving vm disk stats to cloud_usage db", ex);
        }
        return vmDiskStats;
    }

};