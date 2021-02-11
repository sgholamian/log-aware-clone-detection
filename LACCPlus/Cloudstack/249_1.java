//,temp,UsageEventDaoImpl.java,193,211,temp,UsageDaoImpl.java,245,260
//,3
public class xxx {
    private long getMaxEventId(Date endDate) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        PreparedStatement pstmt = null;
        try {
            String sql = MAX_EVENT;
            pstmt = txn.prepareAutoCloseStatement(sql);
            pstmt.setString(1, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), endDate));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Long.valueOf(rs.getLong(1));
            }
            return 0;
        } catch (Exception ex) {
            s_logger.error("error getting max event id", ex);
            throw new CloudRuntimeException(ex.getMessage());
        } finally {
            txn.close();
        }
    }

};