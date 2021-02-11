//,temp,UsageDaoImpl.java,279,296,temp,UserStatisticsDaoImpl.java,95,115
//,3
public class xxx {
    @Override
    public List<Long> listPublicTemplatesByAccount(long accountId) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        PreparedStatement pstmt = null;
        String sql = GET_PUBLIC_TEMPLATES_BY_ACCOUNTID;
        List<Long> templateList = new ArrayList<Long>();
        try {
            pstmt = txn.prepareAutoCloseStatement(sql);
            pstmt.setLong(1, accountId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                templateList.add(Long.valueOf(rs.getLong(1)));
            }
        } catch (Exception ex) {
            s_logger.error("error listing public templates", ex);
        }
        return templateList;
    }

};