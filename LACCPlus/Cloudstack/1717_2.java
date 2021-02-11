//,temp,UsageDaoImpl.java,279,296,temp,ConsoleProxyDaoImpl.java,204,221
//,3
public class xxx {
    @Override
    public List<Pair<Long, Integer>> getProxyLoadMatrix() {
        ArrayList<Pair<Long, Integer>> l = new ArrayList<Pair<Long, Integer>>();

        TransactionLegacy txn = TransactionLegacy.currentTxn();
        ;
        PreparedStatement pstmt = null;
        try {
            pstmt = txn.prepareAutoCloseStatement(PROXY_ASSIGNMENT_MATRIX);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                l.add(new Pair<Long, Integer>(rs.getLong(1), rs.getInt(2)));
            }
        } catch (SQLException e) {
            s_logger.debug("Caught SQLException: ", e);
        }
        return l;
    }

};