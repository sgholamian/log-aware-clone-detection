//,temp,ConsoleProxyDaoImpl.java,307,327,temp,ConsoleProxyDaoImpl.java,285,305
//,3
public class xxx {
    @Override
    public List<Long> getRunningProxyListByMsid(long msid) {
        List<Long> l = new ArrayList<Long>();
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        ;
        PreparedStatement pstmt = null;
        try {
            pstmt =
                txn.prepareAutoCloseStatement("SELECT c.id FROM console_proxy c, vm_instance v, host h "
                    + "WHERE c.id=v.id AND v.state='Running' AND v.host_id=h.id AND h.mgmt_server_id=?");

            pstmt.setLong(1, msid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                l.add(rs.getLong(1));
            }
        } catch (SQLException e) {
            s_logger.debug("Caught SQLException: ", e);
        }
        return l;
    }

};