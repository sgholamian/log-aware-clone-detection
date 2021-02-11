//,temp,VMTemplateHostDaoImpl.java,155,175,temp,ManagementServerHostDaoImpl.java,79,104
//,3
public class xxx {
    @Override
    public void update(VMTemplateHostVO instance) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        PreparedStatement pstmt = null;
        try {
            Date now = new Date();
            String sql = UPDATE_TEMPLATE_HOST_REF;
            pstmt = txn.prepareAutoCloseStatement(sql);
            pstmt.setString(1, instance.getDownloadState().toString());
            pstmt.setInt(2, instance.getDownloadPercent());
            pstmt.setString(3, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), now));
            pstmt.setString(4, instance.getErrorString());
            pstmt.setString(5, instance.getLocalDownloadPath());
            pstmt.setString(6, instance.getJobId());
            pstmt.setLong(7, instance.getHostId());
            pstmt.setLong(8, instance.getTemplateId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            s_logger.warn("Exception: ", e);
        }
    }

};