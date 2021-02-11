//,temp,Upgrade302to303.java,142,167,temp,Upgrade410to420.java,1500,1519
//,3
public class xxx {
    private void addSrxFirewall(Connection conn, long hostId, long physicalNetworkId) {
        String insertSrx =
                "INSERT INTO `cloud`.`external_firewall_devices` (physical_network_id, host_id, provider_name, "
                        + "device_name, capacity, is_dedicated, device_state, allocation_state, uuid) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement pstmtUpdate = conn.prepareStatement(insertSrx);) {
            s_logger.debug("Adding SRX firewall device with host id " + hostId + " in to physical network" + physicalNetworkId);
            pstmtUpdate.setLong(1, physicalNetworkId);
            pstmtUpdate.setLong(2, hostId);
            pstmtUpdate.setString(3, "JuniperSRX");
            pstmtUpdate.setString(4, "JuniperSRXFirewall");
            pstmtUpdate.setLong(5, 0);
            pstmtUpdate.setBoolean(6, false);
            pstmtUpdate.setString(7, "Enabled");
            pstmtUpdate.setString(8, "Shared");
            pstmtUpdate.setString(9, UUID.randomUUID().toString());
            pstmtUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new CloudRuntimeException("Exception while adding SRX firewall device ", e);
        }
    }

};