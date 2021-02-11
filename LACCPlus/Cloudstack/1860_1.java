//,temp,ManagementServerHostDaoImpl.java,210,230,temp,ManagementServerHostDaoImpl.java,126,150
//,3
public class xxx {
    @Override
    public void update(long id, long runId, State state, Date lastUpdate) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        PreparedStatement pstmt = null;
        try {
            pstmt = txn.prepareAutoCloseStatement("update mshost set state=?, last_update=? where id=? and runid=?");
            pstmt.setString(1, state.toString());
            pstmt.setString(2, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), lastUpdate));
            pstmt.setLong(3, id);
            pstmt.setLong(4, runId);

            int count = pstmt.executeUpdate();

            if (count < 1) {
                s_logger.info("Invalid cluster session detected, runId " + runId + " is no longer valid");
                throw new CloudRuntimeException("Invalid cluster session detected, runId " + runId + " is no longer valid", new ClusterInvalidSessionException("runId " + runId + " is no longer valid"));
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("DB exception: ", e);
        }
    }

};