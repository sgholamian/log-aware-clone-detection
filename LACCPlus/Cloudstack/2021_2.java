//,temp,Upgrade302to303.java,169,192,temp,Upgrade302to303.java,142,167
//,3
public class xxx {
    private void addF5LoadBalancer(Connection conn, long hostId, long physicalNetworkId) {
        PreparedStatement pstmtUpdate = null;
        try {
            s_logger.debug("Adding F5 Big IP load balancer with host id " + hostId + " in to physical network" + physicalNetworkId);
            String insertF5 =
                "INSERT INTO `cloud`.`external_load_balancer_devices` (physical_network_id, host_id, provider_name, "
                    + "device_name, capacity, is_dedicated, device_state, allocation_state, is_inline, is_managed, uuid) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmtUpdate = conn.prepareStatement(insertF5);
            pstmtUpdate.setLong(1, physicalNetworkId);
            pstmtUpdate.setLong(2, hostId);
            pstmtUpdate.setString(3, "F5BigIp");
            pstmtUpdate.setString(4, "F5BigIpLoadBalancer");
            pstmtUpdate.setLong(5, 0);
            pstmtUpdate.setBoolean(6, false);
            pstmtUpdate.setString(7, "Enabled");
            pstmtUpdate.setString(8, "Shared");
            pstmtUpdate.setBoolean(9, false);
            pstmtUpdate.setBoolean(10, false);
            pstmtUpdate.setString(11, UUID.randomUUID().toString());
            pstmtUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new CloudRuntimeException("Exception while adding F5 load balancer device", e);
        } finally {
            closeAutoCloseable(pstmtUpdate);
        }
    }

};