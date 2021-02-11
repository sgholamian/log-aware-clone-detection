//,temp,Upgrade218to22.java,2401,2436,temp,Upgrade218to22.java,2366,2396
//,3
public class xxx {
    private void createNetworkOfferingEvents(Connection conn) {
        s_logger.debug("Creating network offering usage events");
        try (
                PreparedStatement pstmt =
                    conn.prepareStatement("SELECT vm.account_id, vm.data_center_id, ni.instance_id, vm.name, nw.network_offering_id, nw.is_default FROM nics ni, "
                            + "networks nw, vm_instance vm where vm.type = 'User' and ni.removed is null and ni.instance_id = vm.id and ni.network_id = nw.id;");
                ResultSet rs = pstmt.executeQuery();
            ) {
            Date now = new Date();
            while (rs.next()) {
                long accountId = rs.getLong(1);
                long zoneId = rs.getLong(2);
                long vmId = rs.getLong(3);
                String vmName = rs.getString(4);
                long nw_offering_id = rs.getLong(5);
                long isDefault = rs.getLong(6);
                try (PreparedStatement pstmt1 =
                    conn.prepareStatement(
                            "INSERT INTO usage_event (usage_event.type, usage_event.created, usage_event.account_id, usage_event.zone_id, usage_event.resource_id, usage_event.resource_name, "
                           + "usage_event.offering_id, usage_event.size) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"); ) {
                    pstmt1.setString(1, EventTypes.EVENT_NETWORK_OFFERING_ASSIGN);
                    pstmt1.setString(2, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), now));
                    pstmt1.setLong(3, accountId);
                    pstmt1.setLong(4, zoneId);
                    pstmt1.setLong(5, vmId);
                    pstmt1.setString(6, vmName);
                    pstmt1.setLong(7, nw_offering_id);
                    pstmt1.setLong(8, isDefault);
                    pstmt1.executeUpdate();
                }
            }
            s_logger.debug("Completed creating network offering usage events");
        } catch (SQLException e) {
            throw new CloudRuntimeException("Failed to add network offering usage events due to:", e);
        }
    }

};