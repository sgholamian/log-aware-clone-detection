//,temp,Upgrade410to420.java,1478,1498,temp,Upgrade302to40.java,718,737
//,3
public class xxx {
    private void addF5LoadBalancer(Connection conn, long hostId, long physicalNetworkId) {
        String insertF5 =
                "INSERT INTO `cloud`.`external_load_balancer_devices` (physical_network_id, host_id, provider_name, "
                        + "device_name, capacity, is_dedicated, device_state, allocation_state, is_managed, uuid) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement pstmtUpdate =  conn.prepareStatement(insertF5);) {
            s_logger.debug("Adding F5 Big IP load balancer with host id " + hostId + " in to physical network" + physicalNetworkId);
            pstmtUpdate.setLong(1, physicalNetworkId);
            pstmtUpdate.setLong(2, hostId);
            pstmtUpdate.setString(3, "F5BigIp");
            pstmtUpdate.setString(4, "F5BigIpLoadBalancer");
            pstmtUpdate.setLong(5, 0);
            pstmtUpdate.setBoolean(6, false);
            pstmtUpdate.setString(7, "Enabled");
            pstmtUpdate.setString(8, "Shared");
            pstmtUpdate.setBoolean(9, false);
            pstmtUpdate.setString(10, UUID.randomUUID().toString());
            pstmtUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new CloudRuntimeException("Exception while adding F5 load balancer device", e);
        }
    }

};