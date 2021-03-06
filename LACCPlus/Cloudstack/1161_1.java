//,temp,Upgrade218to22.java,2366,2396,temp,Upgrade430to440.java,203,228
//,3
public class xxx {
    private void createLoadBalancerEvents(Connection conn) {
        s_logger.debug("Creating load balancer usage events");
        try (
                PreparedStatement pstmt =
                    conn.prepareStatement("SELECT fw.account_id, ip.data_center_id, fw.id FROM firewall_rules fw, user_ip_address ip where purpose = 'LoadBalancing' and "
                            + "fw.state = 'Active' and ip.id = fw.ip_address_id");
                ResultSet rs = pstmt.executeQuery();
            ) {
            Date now = new Date();
            while (rs.next()) {
                long accountId = rs.getLong(1);
                long zoneId = rs.getLong(2);
                long ruleId = rs.getLong(3);
                try (
                        PreparedStatement pstmt1 =
                        conn.prepareStatement("INSERT INTO usage_event (usage_event.type, usage_event.created, usage_event.account_id, usage_event.zone_id, usage_event.resource_id)"
                                + " VALUES (?, ?, ?, ?, ?)");
                    ) {
                    pstmt1.setString(1, EventTypes.EVENT_LOAD_BALANCER_CREATE);
                    pstmt1.setString(2, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), now));
                    pstmt1.setLong(3, accountId);
                    pstmt1.setLong(4, zoneId);
                    pstmt1.setLong(5, ruleId);
                    pstmt1.executeUpdate();
                }
            }
            s_logger.debug("Completed creating load balancer usage events");
        } catch (SQLException e) {
            throw new CloudRuntimeException("Failed to add Load Balancer usage events due to:", e);
        }
    }

};