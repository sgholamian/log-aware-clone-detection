//,temp,VMTemplateHostDaoImpl.java,155,175,temp,ManagementServerHostDaoImpl.java,79,104
//,3
public class xxx {
    @Override
    @DB
    public void update(long id, long runid, String name, String version, String serviceIP, int servicePort, Date lastUpdate) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        PreparedStatement pstmt = null;
        try {
            txn.start();

            pstmt =
                txn.prepareAutoCloseStatement("update mshost set name=?, version=?, service_ip=?, service_port=?, last_update=?, removed=null, alert_count=0, runid=?, state=? where id=?");
            pstmt.setString(1, name);
            pstmt.setString(2, version);
            pstmt.setString(3, serviceIP);
            pstmt.setInt(4, servicePort);
            pstmt.setString(5, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), lastUpdate));
            pstmt.setLong(6, runid);
            pstmt.setString(7, ManagementServerHost.State.Up.toString());
            pstmt.setLong(8, id);

            pstmt.executeUpdate();
            txn.commit();
        } catch (Exception e) {
            s_logger.warn("Unexpected exception, ", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

};