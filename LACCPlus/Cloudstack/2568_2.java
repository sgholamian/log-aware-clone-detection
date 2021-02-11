//,temp,VMTemplatePoolDaoImpl.java,188,213,temp,VMTemplateHostDaoImpl.java,256,281
//,3
public class xxx {
    @Override
    public List<VMTemplateHostVO> listByTemplateStatus(long templateId, long datacenterId, long podId, VMTemplateHostVO.Status downloadState) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        List<VMTemplateHostVO> result = new ArrayList<VMTemplateHostVO>();
        String sql = DOWNLOADS_STATE_DC_POD;
        try(PreparedStatement pstmt = txn.prepareStatement(sql);) {
            pstmt.setLong(1, datacenterId);
            pstmt.setLong(2, podId);
            pstmt.setLong(3, templateId);
            pstmt.setString(4, downloadState.toString());
            try(ResultSet rs = pstmt.executeQuery();) {
                while (rs.next()) {
                    // result.add(toEntityBean(rs, false)); TODO: this is buggy in
                    // GenericDaoBase for hand constructed queries
                    long id = rs.getLong(1); // ID column
                    result.add(findById(id));
                }
            }catch (SQLException e) {
                s_logger.warn("listByTemplateStatus:Exception: "+e.getMessage(), e);
            }
        } catch (Exception e) {
            s_logger.warn("listByTemplateStatus:Exception: "+e.getMessage(), e);
        }
        return result;

    }

};