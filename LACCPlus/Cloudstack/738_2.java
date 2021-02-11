//,temp,ConsoleProxyDaoImpl.java,267,283,temp,ConsoleProxyDaoImpl.java,248,265
//,2
public class xxx {
    @Override
    public int getProxyStaticLoad(long proxyVmId) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        ;
        PreparedStatement pstmt = null;
        try {
            pstmt = txn.prepareAutoCloseStatement(GET_PROXY_LOAD);
            pstmt.setLong(1, proxyVmId);

            ResultSet rs = pstmt.executeQuery();
            if (rs != null && rs.first()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            s_logger.debug("Caught SQLException: ", e);
        }
        return 0;
    }

};