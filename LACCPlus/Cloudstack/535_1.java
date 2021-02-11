//,temp,Upgrade305to306.java,134,194,temp,Upgrade301to302.java,94,158
//,3
public class xxx {
    private void upgradeEgressFirewallRules(Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSet rsId = null;
        ResultSet rsNw = null;
        try {
            // update the existing ingress rules traffic type
            pstmt = conn.prepareStatement("update `cloud`.`firewall_rules`" +
                "  set traffic_type='Ingress' where purpose='Firewall' and ip_address_id is not null and traffic_type is null");
            s_logger.debug("Updating firewall Ingress rule traffic type: " + pstmt);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement("select network_id FROM `cloud`.`ntwk_service_map` where service='Firewall' and provider='VirtualRouter' ");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                long netId = rs.getLong(1);
                //When upgraded from 2.2.14 to 3.0.6 guest_type is updated to Isolated in the 2214to30 clean up sql. clean up executes
                //after this. So checking for Isolated OR Virtual
                pstmt = conn.prepareStatement("select account_id, domain_id FROM `cloud`.`networks` where (guest_type='Isolated' OR guest_type='" +
                    "Virtual') and traffic_type='Guest' and vpc_id is NULL and (state='implemented' OR state='Shutdown') and id=? ");
                pstmt.setLong(1, netId);
                s_logger.debug("Getting account_id, domain_id from networks table: " + pstmt);
                rsNw = pstmt.executeQuery();

                if (rsNw.next()) {
                    long accountId = rsNw.getLong(1);
                    long domainId = rsNw.getLong(2);

                    //Add new rule for the existing networks
                    s_logger.debug("Adding default egress firewall rule for network " + netId);
                    pstmt =
                        conn.prepareStatement("INSERT INTO firewall_rules (uuid, state, protocol, purpose, account_id, domain_id, network_id, xid, created,  traffic_type) VALUES (?, 'Active', 'all', 'Firewall', ?, ?, ?, ?, now(), 'Egress')");
                    pstmt.setString(1, UUID.randomUUID().toString());
                    pstmt.setLong(2, accountId);
                    pstmt.setLong(3, domainId);
                    pstmt.setLong(4, netId);
                    pstmt.setString(5, UUID.randomUUID().toString());
                    s_logger.debug("Inserting default egress firewall rule " + pstmt);
                    pstmt.executeUpdate();

                    pstmt = conn.prepareStatement("select id from firewall_rules where protocol='all' and network_id=?");
                    pstmt.setLong(1, netId);
                    rsId = pstmt.executeQuery();

                    long firewallRuleId;
                    if (rsId.next()) {
                        firewallRuleId = rsId.getLong(1);
                        pstmt = conn.prepareStatement("insert into firewall_rules_cidrs (firewall_rule_id,source_cidr) values (?, '0.0.0.0/0')");
                        pstmt.setLong(1, firewallRuleId);
                        s_logger.debug("Inserting rule for cidr 0.0.0.0/0 for the new Firewall rule id=" + firewallRuleId + " with statement " + pstmt);
                        pstmt.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to set egress firewall rules ", e);
        } finally {
            closeAutoCloseable(rs);
            closeAutoCloseable(pstmt);
        }
    }

};