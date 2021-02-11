//,temp,ConsoleProxyDaoImpl.java,307,327,temp,ConsoleProxyDaoImpl.java,285,305
//,3
public class xxx {
    private List<ConsoleProxyLoadInfo> getDatacenterLoadMatrix(String sql) {
        ArrayList<ConsoleProxyLoadInfo> l = new ArrayList<ConsoleProxyLoadInfo>();

        TransactionLegacy txn = TransactionLegacy.currentTxn();
        ;
        PreparedStatement pstmt = null;
        try {
            pstmt = txn.prepareAutoCloseStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ConsoleProxyLoadInfo info = new ConsoleProxyLoadInfo();
                info.setId(rs.getLong(1));
                info.setName(rs.getString(2));
                info.setCount(rs.getInt(3));
                l.add(info);
            }
        } catch (SQLException e) {
            s_logger.debug("Exception: ", e);
        }
        return l;
    }

};