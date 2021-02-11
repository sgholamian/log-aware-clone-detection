//,temp,EngineHostPodDaoImpl.java,92,119,temp,HostPodDaoImpl.java,81,108
//,2
public class xxx {
    @Override
    public HashMap<Long, List<Object>> getCurrentPodCidrSubnets(long zoneId, long podIdToSkip) {
        HashMap<Long, List<Object>> currentPodCidrSubnets = new HashMap<Long, List<Object>>();

        String selectSql = "SELECT id, cidr_address, cidr_size FROM host_pod_ref WHERE data_center_id=" + zoneId + " and removed IS NULL";
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        try {
            PreparedStatement stmt = txn.prepareAutoCloseStatement(selectSql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Long podId = rs.getLong("id");
                if (podId.longValue() == podIdToSkip) {
                    continue;
                }
                String cidrAddress = rs.getString("cidr_address");
                long cidrSize = rs.getLong("cidr_size");
                List<Object> cidrPair = new ArrayList<Object>();
                cidrPair.add(0, cidrAddress);
                cidrPair.add(1, new Long(cidrSize));
                currentPodCidrSubnets.put(podId, cidrPair);
            }
        } catch (SQLException ex) {
            s_logger.warn("DB exception " + ex.getMessage(), ex);
            return null;
        }

        return currentPodCidrSubnets;
    }

};