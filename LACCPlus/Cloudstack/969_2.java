//,temp,UsageDaoImpl.java,279,296,temp,VMTemplatePoolDaoImpl.java,166,186
//,3
public class xxx {
    @Override
    public List<VMTemplateStoragePoolVO> listByTemplateStatus(long templateId, long datacenterId, VMTemplateStoragePoolVO.Status downloadState) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        PreparedStatement pstmt = null;
        List<VMTemplateStoragePoolVO> result = new ArrayList<VMTemplateStoragePoolVO>();
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