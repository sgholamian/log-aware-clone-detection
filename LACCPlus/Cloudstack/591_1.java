//,temp,Upgrade40to41.java,97,185,temp,Upgrade410to420.java,282,374
//,3
public class xxx {
    private void upgradeEgressFirewallRules(Connection conn) {

        // update the existing ingress rules traffic type
        try (PreparedStatement updateNwpstmt = conn.prepareStatement("update `cloud`.`firewall_rules`  set traffic_type='Ingress' where purpose='Firewall' and ip_address_id is " +
                "not null and traffic_type is null");)
        {
            updateNwpstmt.executeUpdate();
            s_logger.debug("Updating firewall Ingress rule traffic type: " + updateNwpstmt);
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to update ingress firewall rules ", e);
        }


        try (PreparedStatement vrNwpstmt = conn.prepareStatement("select network_id FROM `cloud`.`ntwk_service_map` where service='Firewall' and provider='VirtualRouter' ");
             ResultSet vrNwsRs = vrNwpstmt.executeQuery();
        ) {
            while (vrNwsRs.next()) {
                long netId = vrNwsRs.getLong(1);
                //When upgraded from 2.2.14 to 3.0.6 guest_type is updated to Isolated in the 2214to30 clean up sql. clean up executes
                //after this. So checking for Isolated OR Virtual
                try (PreparedStatement NwAcctDomIdpstmt = conn.prepareStatement("select account_id, domain_id FROM `cloud`.`networks` where (guest_type='Isolated' OR " +
                        "guest_type='Virtual') and traffic_type='Guest' and vpc_id is NULL and " +
                        "(state='implemented' OR state='Shutdown') and id=? "); ) {
                    NwAcctDomIdpstmt.setLong(1, netId);

                    try (ResultSet NwAcctDomIdps = NwAcctDomIdpstmt.executeQuery();) {
                        s_logger.debug("Getting account_id, domain_id from networks table: " + NwAcctDomIdpstmt);

                        if (NwAcctDomIdps.next()) {
                            long accountId = NwAcctDomIdps.getLong(1);
                            long domainId = NwAcctDomIdps.getLong(2);
                            //Add new rule for the existing networks
                            s_logger.debug("Adding default egress firewall rule for network " + netId);
                            try (PreparedStatement fwRulespstmt = conn.prepareStatement("INSERT INTO firewall_rules "+
                                    " (uuid, state, protocol, purpose, account_id, domain_id, network_id, xid, created,"
                                    + " traffic_type) VALUES (?, 'Active', 'all', 'Firewall', ?, ?, ?, ?, now(), "
                                 +"'Egress')");
                            ) {
                            fwRulespstmt.setString(1, UUID.randomUUID().toString());
                            fwRulespstmt.setLong(2, accountId);
                            fwRulespstmt.setLong(3, domainId);
                            fwRulespstmt.setLong(4, netId);
                            fwRulespstmt.setString(5, UUID.randomUUID().toString());
                            s_logger.debug("Inserting default egress firewall rule " + fwRulespstmt);
                            fwRulespstmt.executeUpdate();
                            }  catch (SQLException e) {
                                throw new CloudRuntimeException("failed to insert default egress firewall rule ", e);
                            }

                            try (PreparedStatement protoAllpstmt = conn.prepareStatement("select id from firewall_rules where protocol='all' and network_id=?");)
                            {
                            protoAllpstmt.setLong(1, netId);

                                try (ResultSet protoAllRs = protoAllpstmt.executeQuery();) {
                                    long firewallRuleId;
                                    if (protoAllRs.next()) {
                                        firewallRuleId = protoAllRs.getLong(1);

                                        try (PreparedStatement fwCidrsPstmt = conn.prepareStatement("insert into firewall_rules_cidrs (firewall_rule_id,source_cidr) values (?, '0.0.0.0/0')");) {
                                            fwCidrsPstmt.setLong(1, firewallRuleId);
                                            s_logger.debug("Inserting rule for cidr 0.0.0.0/0 for the new Firewall rule id=" + firewallRuleId + " with statement " + fwCidrsPstmt);
                                            fwCidrsPstmt.executeUpdate();
                                        }  catch (SQLException e) {
                                            throw new CloudRuntimeException("Unable to set egress firewall rules ", e);
                                        }

                                    }
                                } catch (SQLException e) {
                                    throw new CloudRuntimeException("Unable to set egress firewall rules ", e);
                                }

                            } catch (SQLException e) {
                                throw new CloudRuntimeException("Unable to set egress firewall rules ", e);
                            }

                        } //if
                    } catch (SQLException e) {
                        throw new CloudRuntimeException("Unable execute update query ", e);
                    }

                } catch (SQLException e) {
                    throw new CloudRuntimeException("Unable to get account id domainid of networks ", e);
                }
            } //while
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to set egress firewall rules ", e);

        }
    }

};