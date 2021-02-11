//,temp,VMTemplatePoolDaoImpl.java,166,186,temp,CapacityDaoImpl.java,1068,1087
//,3
public class xxx {
    @Override
    public float findClusterConsumption(Long clusterId, short capacityType, long computeRequested) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        StringBuilder sql = new StringBuilder(FIND_CLUSTER_CONSUMPTION_RATIO);
        PreparedStatement pstmt = null;
        try {
            pstmt = txn.prepareAutoCloseStatement(sql.toString());

            pstmt.setLong(1, computeRequested);
            pstmt.setLong(2, clusterId);
            pstmt.setShort(3, capacityType);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return rs.getFloat(1);
            }
        } catch (Exception e) {
            s_logger.warn("Error checking cluster threshold", e);
        }
        return 0;
    }

};