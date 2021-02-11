//,temp,VMTemplatePoolDaoImpl.java,188,213,temp,StoragePoolHostDaoImpl.java,100,122
//,3
public class xxx {
    @Override
    public List<VMTemplateStoragePoolVO> listByTemplateStatus(long templateId, long datacenterId, long podId, VMTemplateStoragePoolVO.Status downloadState) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        List<VMTemplateStoragePoolVO> result = new ArrayList<VMTemplateStoragePoolVO>();
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
            }catch (Exception e) {
                s_logger.warn("Exception: ", e);
            }
        } catch (Exception e) {
            s_logger.warn("Exception: ", e);
        }
        return result;

    }

};