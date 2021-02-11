//,temp,HostDaoImpl.java,779,797,temp,VMTemplatePoolDaoImpl.java,215,237
//,3
public class xxx {
    @DB
    @Override
    public List<HostVO> findLostHosts(long timeout) {
        List<HostVO> result = new ArrayList<HostVO>();
        String sql = "select h.id from host h left join  cluster c on h.cluster_id=c.id where h.mgmt_server_id is not null and h.last_ping < ? and h.status in ('Up', 'Updating', 'Disconnected', 'Connecting') and h.type not in ('ExternalFirewall', 'ExternalLoadBalancer', 'TrafficMonitor', 'SecondaryStorage', 'LocalSecondaryStorage', 'L2Networking') and (h.cluster_id is null or c.managed_state = 'Managed') ;";
        try (TransactionLegacy txn = TransactionLegacy.currentTxn();
                PreparedStatement pstmt = txn.prepareStatement(sql);) {
            pstmt.setLong(1, timeout);
            try (ResultSet rs = pstmt.executeQuery();) {
                while (rs.next()) {
                    long id = rs.getLong(1); //ID column
                    result.add(findById(id));
                }
            }
        } catch (SQLException e) {
            s_logger.warn("Exception: ", e);
        }
        return result;
    }

};