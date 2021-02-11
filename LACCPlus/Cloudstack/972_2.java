//,temp,UsageDaoImpl.java,279,296,temp,VMTemplatePoolDaoImpl.java,215,237
//,3
public class xxx {
    public List<VMTemplateStoragePoolVO> listByHostTemplate(long hostId, long templateId) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        List<VMTemplateStoragePoolVO> result = new ArrayList<VMTemplateStoragePoolVO>();
        String sql = HOST_TEMPLATE_SEARCH;
        try(PreparedStatement pstmt = txn.prepareStatement(sql);) {
            pstmt.setLong(1, hostId);
            pstmt.setLong(2, templateId);
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