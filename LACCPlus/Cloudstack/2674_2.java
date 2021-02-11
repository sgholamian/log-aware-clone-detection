//,temp,Upgrade302to303.java,169,192,temp,Upgrade30xBase.java,110,131
//,3
public class xxx {
    protected void addTrafficType(Connection conn, long physicalNetworkId, String trafficType, String xenPublicLabel, String kvmPublicLabel, String vmwarePublicLabel) {
        // add traffic types
        PreparedStatement pstmtUpdate = null;
        try {
            s_logger.debug("Adding PhysicalNetwork traffic types");
            String insertTraficType =
                "INSERT INTO `cloud`.`physical_network_traffic_types` (physical_network_id, traffic_type, xen_network_label, kvm_network_label, vmware_network_label, uuid) VALUES ( ?, ?, ?, ?, ?, ?)";
            pstmtUpdate = conn.prepareStatement(insertTraficType);
            pstmtUpdate.setLong(1, physicalNetworkId);
            pstmtUpdate.setString(2, trafficType);
            pstmtUpdate.setString(3, xenPublicLabel);
            pstmtUpdate.setString(4, kvmPublicLabel);
            pstmtUpdate.setString(5, vmwarePublicLabel);
            pstmtUpdate.setString(6, UUID.randomUUID().toString());
            pstmtUpdate.executeUpdate();
            pstmtUpdate.close();
        } catch (SQLException e) {
            throw new CloudRuntimeException("Exception while adding PhysicalNetworks", e);
        } finally {
            closeAutoCloseable(pstmtUpdate);
        }
    }

};