//,temp,VMTemplatePoolDaoImpl.java,166,186,temp,StoragePoolHostDaoImpl.java,100,122
//,3
public class xxx {
    @Override
    public List<StoragePoolHostVO> listByHostStatus(long poolId, Status hostStatus) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        String sql = HOST_FOR_POOL_SEARCH;
        List<StoragePoolHostVO> result = new ArrayList<StoragePoolHostVO>();
        try(PreparedStatement pstmt = txn.prepareStatement(sql);) {
            pstmt.setLong(1, poolId);
            pstmt.setString(2, hostStatus.toString());
            try(ResultSet rs = pstmt.executeQuery();) {
                while (rs.next()) {
                    // result.add(toEntityBean(rs, false)); TODO: this is buggy in
                    // GenericDaoBase for hand constructed queries
                    long id = rs.getLong(1); // ID column
                    result.add(findById(id));
                }
            }catch (SQLException e) {
                s_logger.warn("listByHostStatus:Exception: ", e);
            }
        } catch (Exception e) {
            s_logger.warn("listByHostStatus:Exception: ", e);
        }
        return result;
    }

};