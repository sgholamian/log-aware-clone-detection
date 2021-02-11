//,temp,VmDiskStatisticsDaoImpl.java,90,110,temp,UserStatisticsDaoImpl.java,95,115
//,2
public class xxx {
    @Override
    public List<UserStatisticsVO> listActiveAndRecentlyDeleted(Date minRemovedDate, int startIndex, int limit) {
        List<UserStatisticsVO> userStats = new ArrayList<UserStatisticsVO>();
        if (minRemovedDate == null)
            return userStats;

        TransactionLegacy txn = TransactionLegacy.currentTxn();
        try {
            String sql = ACTIVE_AND_RECENTLY_DELETED_SEARCH + " LIMIT " + startIndex + "," + limit;
            PreparedStatement pstmt = null;
            pstmt = txn.prepareAutoCloseStatement(sql);
            pstmt.setString(1, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), minRemovedDate));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                userStats.add(toEntityBean(rs, false));
            }
        } catch (Exception ex) {
            s_logger.error("error saving user stats to cloud_usage db", ex);
        }
        return userStats;
    }

};