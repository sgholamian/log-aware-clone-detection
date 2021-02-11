//,temp,Upgrade218to22.java,1343,1461,temp,Upgrade218to22.java,1247,1341
//,3
public class xxx {
    public void upgradePortForwardingRules(Connection conn) {
        try (
                PreparedStatement pstmt =
                    conn.prepareStatement("SELECT id, public_ip_address, public_port, private_ip_address, private_port, protocol FROM ip_forwarding WHERE forwarding=1");
                ResultSet rs = pstmt.executeQuery();
            ) {
            ArrayList<Object[]> rules = new ArrayList<Object[]>();
            while (rs.next()) {
                Object[] rule = new Object[10];
                rule[0] = rs.getLong(1); // rule id
                rule[1] = rs.getString(2); // rule public IP
                rule[2] = rs.getString(3); // rule public port
                rule[3] = rs.getString(4); // rule private Ip
                rule[4] = rs.getString(5); // rule private port
                rule[5] = rs.getString(6); // rule protocol
                rules.add(rule);
            }

            if (!rules.isEmpty()) {
                s_logger.debug("Found " + rules.size() + " port forwarding rules to upgrade");
                for (Object[] rule : rules) {
                    long id = (Long)rule[0];
                    String sourcePort = (String)rule[2];
                    String protocol = (String)rule[5];
                    String publicIp = (String)rule[1];

                    try (PreparedStatement selectUserIpAddressData = conn.prepareStatement("SELECT id, account_id, domain_id, network_id FROM user_ip_address WHERE public_ip_address=?");) {
                        selectUserIpAddressData.setString(1, publicIp);
                        try (ResultSet userIpAddressData = selectUserIpAddressData.executeQuery();) {

                            if (!userIpAddressData.next()) {
                                s_logger.error("Unable to find public IP address " + publicIp);
                                throw new CloudRuntimeException("Unable to find public IP address " + publicIp);
                            }
                            int ipAddressId = userIpAddressData.getInt(1);
                            long accountId = userIpAddressData.getLong(2);
                            long domainId = userIpAddressData.getLong(3);
                            long networkId = userIpAddressData.getLong(4);
                            String privateIp = (String)rule[3];

                            // update port_forwarding_rules table
                            s_logger.trace("Updating port_forwarding_rules table...");
                            try (PreparedStatement selectInstanceId = conn.prepareStatement("SELECT instance_id FROM nics where network_id=? AND ip4_address=?");) {
                                selectInstanceId.setLong(1, networkId);
                                selectInstanceId.setString(2, privateIp);
                                try (ResultSet selectedInstanceId = selectInstanceId.executeQuery();) {

                                    if (!selectedInstanceId.next()) {
                                        // the vm might be expunged already...so just give the warning
                                        s_logger.warn("Unable to find vmId for private ip address " + privateIp + " for account id=" + accountId + "; assume that the vm is expunged");
                                        // throw new CloudRuntimeException("Unable to find vmId for private ip address " + privateIp +
                                        // " for account id=" + accountId);
                                    } else {
                                        long instanceId = selectedInstanceId.getLong(1);
                                        s_logger.debug("Instance id is " + instanceId);
                                        // update firewall_rules table
                                        s_logger.trace("Updating firewall_rules table as a part of PF rules upgrade...");
                                        try (
                                                PreparedStatement insertFirewallRules =
                                                conn.prepareStatement("INSERT INTO firewall_rules (id, ip_address_id, start_port, end_port, state, protocol, purpose, account_id, domain_id, network_id, xid, is_static_nat, created) VALUES (?,    ?,      ?,      ?,      'Active',        ?,     'PortForwarding',       ?,      ?,      ?,      ?,       0,     now())");
                                            ) {
                                            insertFirewallRules.setLong(1, id);
                                            insertFirewallRules.setInt(2, ipAddressId);
                                            insertFirewallRules.setInt(3, Integer.parseInt(sourcePort.trim()));
                                            insertFirewallRules.setInt(4, Integer.parseInt(sourcePort.trim()));
                                            insertFirewallRules.setString(5, protocol);
                                            insertFirewallRules.setLong(6, accountId);
                                            insertFirewallRules.setLong(7, domainId);
                                            insertFirewallRules.setLong(8, networkId);
                                            insertFirewallRules.setString(9, UUID.randomUUID().toString());
                                            insertFirewallRules.executeUpdate();
                                            s_logger.trace("firewall_rules table is updated as a part of PF rules upgrade");
                                        }
                                        String privatePort = (String)rule[4];
                                        try (PreparedStatement insertPortForwardingRules = conn.prepareStatement("INSERT INTO port_forwarding_rules VALUES (?,    ?,      ?,      ?,       ?)");) {
                                            insertPortForwardingRules.setLong(1, id);
                                            insertPortForwardingRules.setLong(2, instanceId);
                                            insertPortForwardingRules.setString(3, privateIp);
                                            insertPortForwardingRules.setInt(4, Integer.parseInt(privatePort.trim()));
                                            insertPortForwardingRules.setInt(5, Integer.parseInt(privatePort.trim()));
                                            insertPortForwardingRules.executeUpdate();
                                        }
                                        s_logger.trace("port_forwarding_rules table is updated");
                                    }
                                }
                            }
                        }
                    }
                }
            }
            s_logger.debug("Port forwarding rules are updated");
        } catch (SQLException e) {
            throw new CloudRuntimeException("Can't update port forwarding rules ", e);
        }
    }

};