//,temp,sample_5365.java,2,17,temp,sample_2719.java,2,17
//,2
public class xxx {
public void dummy_method(){
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


log.info("error lisitng updated user stats");
}
}

};