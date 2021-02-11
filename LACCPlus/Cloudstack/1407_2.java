//,temp,Upgrade40to41.java,97,185,temp,Upgrade410to420.java,952,1010
//,3
public class xxx {
    private void addEgressFwRulesForSRXGuestNw(Connection conn) {
        ResultSet rs = null;
        try(PreparedStatement pstmt = conn.prepareStatement("select network_id FROM `cloud`.`ntwk_service_map` where service='Firewall' and provider='JuniperSRX' ");) {
            rs = pstmt.executeQuery();
            while (rs.next()) {
                long netId = rs.getLong(1);
                //checking for Isolated OR Virtual
                try(PreparedStatement sel_net_pstmt =
                        conn.prepareStatement("select account_id, domain_id FROM `cloud`.`networks` where (guest_type='Isolated' OR guest_type='Virtual') and traffic_type='Guest' and vpc_id is NULL and (state='implemented' OR state='Shutdown') and id=? ");) {
                    sel_net_pstmt.setLong(1, netId);
                    s_logger.debug("Getting account_id, domain_id from networks table: ");
                    try(ResultSet rsNw = pstmt.executeQuery();)
                    {
                        if (rsNw.next()) {
                            long accountId = rsNw.getLong(1);
                            long domainId = rsNw.getLong(2);

                            //Add new rule for the existing networks
                            s_logger.debug("Adding default egress firewall rule for network " + netId);
                            try (PreparedStatement insert_pstmt =
                                         conn.prepareStatement("INSERT INTO firewall_rules (uuid, state, protocol, purpose, account_id, domain_id, network_id, xid, created,  traffic_type) VALUES (?, 'Active', 'all', 'Firewall', ?, ?, ?, ?, now(), 'Egress')");) {
                                insert_pstmt.setString(1, UUID.randomUUID().toString());
                                insert_pstmt.setLong(2, accountId);
                                insert_pstmt.setLong(3, domainId);
                                insert_pstmt.setLong(4, netId);
                                insert_pstmt.setString(5, UUID.randomUUID().toString());
                                s_logger.debug("Inserting default egress firewall rule " + insert_pstmt);
                                insert_pstmt.executeUpdate();
                            } catch (SQLException e) {
                                throw new CloudRuntimeException("Unable to set egress firewall rules ", e);
                            }
                            try (PreparedStatement sel_firewall_pstmt = conn.prepareStatement("select id from firewall_rules where protocol='all' and network_id=?");) {
                                sel_firewall_pstmt.setLong(1, netId);
                                try (ResultSet rsId = sel_firewall_pstmt.executeQuery();) {
                                    long firewallRuleId;
                                    if (rsId.next()) {
                                        firewallRuleId = rsId.getLong(1);
                                        try (PreparedStatement insert_pstmt = conn.prepareStatement("insert into firewall_rules_cidrs (firewall_rule_id,source_cidr) values (?, '0.0.0.0/0')");) {
                                            insert_pstmt.setLong(1, firewallRuleId);
                                            s_logger.debug("Inserting rule for cidr 0.0.0.0/0 for the new Firewall rule id=" + firewallRuleId + " with statement " + insert_pstmt);
                                            insert_pstmt.executeUpdate();
                                        } catch (SQLException e) {
                                            throw new CloudRuntimeException("Unable to set egress firewall rules ", e);
                                        }
                                    }
                                } catch (SQLException e) {
                                    throw new CloudRuntimeException("Unable to set egress firewall rules ", e);
                                }
                            } catch (SQLException e) {
                                throw new CloudRuntimeException("Unable to set egress firewall rules ", e);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to set egress firewall rules ", e);
        }
    }

};