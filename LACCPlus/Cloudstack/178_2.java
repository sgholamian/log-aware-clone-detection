//,temp,UsageDaoImpl.java,279,296,temp,VMTemplateHostDaoImpl.java,226,245
//,3
public class xxx {
    @Override
    public List<VMTemplateHostVO> listByTemplateStatus(long templateId, long datacenterId, VMTemplateHostVO.Status downloadState) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        PreparedStatement pstmt = null;
        List<VMTemplateHostVO> result = new ArrayList<VMTemplateHostVO>();
        try {
            String sql = DOWNLOADS_STATE_DC;
            pstmt = txn.prepareAutoCloseStatement(sql);
            pstmt.setLong(1, datacenterId);
            pstmt.setLong(2, templateId);
            pstmt.setString(3, downloadState.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(toEntityBean(rs, false));
            }
        } catch (Exception e) {
            s_logger.warn("Exception: ", e);
        }
        return result;
    }

};