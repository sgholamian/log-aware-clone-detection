//,temp,VmDiskStatisticsDaoImpl.java,112,128,temp,UserStatisticsDaoImpl.java,117,133
//,2
public class xxx {
    @Override
    public List<UserStatisticsVO> listUpdatedStats() {
        List<UserStatisticsVO> userStats = new ArrayList<UserStatisticsVO>();

        TransactionLegacy txn = TransactionLegacy.currentTxn();
        try {
            PreparedStatement pstmt = null;
            pstmt = txn.prepareAutoCloseStatement(UPDATED_STATS_SEARCH);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                userStats.add(toEntityBean(rs, false));
            }
        } catch (Exception ex) {
            s_logger.error("error lisitng updated user stats", ex);
        }
        return userStats;
    }

};