//,temp,Upgrade218to22.java,1343,1461,temp,Upgrade218to22.java,1247,1341
//,3
public class xxx {
    public void upgradeLoadBalancingRules(Connection conn) {
        try (
                PreparedStatement pstmt = conn.prepareStatement("SELECT name, ip_address, public_port, private_port, algorithm, id FROM load_balancer");
                ResultSet rs = pstmt.executeQuery();
            ) {
            ArrayList<Object[]> lbs = new ArrayList<Object[]>();
            while (rs.next()) {
                Object[] lb = new Object[10];
                lb[0] = rs.getString(1); // lb name
                lb[1] = rs.getString(2); // lb public IP
                lb[2] = rs.getString(3); // lb public port
                lb[3] = rs.getString(4); // lb private port
                lb[4] = rs.getString(5); // lb algorithm
                lb[5] = rs.getLong(6); // lb Id
                lbs.add(lb);
            }

            if (!lbs.isEmpty()) {
                s_logger.debug("Found " + lbs.size() + " lb rules to upgrade");
                long newLbId = 0;
                try (
                        PreparedStatement selectFWRules = conn.prepareStatement("SELECT max(id) FROM firewall_rules order by id");
                        ResultSet fwRules = selectFWRules.executeQuery();
                    ) {
                    if (rs.next()) {
                        newLbId = rs.getLong(1);
                    }
                }
                for (Object[] lb : lbs) {
                    String name = (String)lb[0];
                    String publicIp = (String)lb[1];
                    String sourcePort = (String)lb[2];
                    String destPort = (String)lb[3];
                    String algorithm = (String)lb[4];
                    Long originalLbId = (Long)lb[5];
                    newLbId = newLbId + 1;

                    try (PreparedStatement selectIpData = conn.prepareStatement("SELECT id, account_id, domain_id, network_id FROM user_ip_address WHERE public_ip_address=?");) {
                        selectIpData.setString(1, publicIp);
                        try (ResultSet ipData = selectIpData.executeQuery();) {

                            if (!ipData.next()) {
                                s_logger.warn("Unable to find public IP address " + publicIp + "; skipping lb rule id=" + originalLbId +
                                        " from update. Cleaning it up from load_balancer_vm_map and load_balancer table");
                                try (PreparedStatement deleteLbVmMap = conn.prepareStatement("DELETE from load_balancer_vm_map where load_balancer_id=?");) {
                                    deleteLbVmMap.setLong(1, originalLbId);
                                    deleteLbVmMap.executeUpdate();
                                }
                                try (PreparedStatement deleteLoadBalancer = conn.prepareStatement("DELETE from load_balancer where id=?");) {
                                    deleteLoadBalancer.setLong(1, originalLbId);
                                    deleteLoadBalancer.executeUpdate();
                                }
                                continue;
                            }
                            int ipAddressId = ipData.getInt(1);
                            long accountId = ipData.getLong(2);
                            long domainId = ipData.getLong(3);
                            long networkId = ipData.getLong(4);
                            // update firewall_rules table
                            s_logger.trace("Updating firewall_rules table as a part of LB rules upgrade...");
                            try (PreparedStatement insertFirewallRules =
                                conn.prepareStatement("INSERT INTO firewall_rules (id, ip_address_id, start_port, end_port, state, protocol, purpose, account_id, domain_id, network_id, xid, is_static_nat, created) VALUES (?,    ?,      ?,      ?,      'Active',        ?,     'LoadBalancing',       ?,      ?,      ?,      ?,       0,       now())");) {
                                insertFirewallRules.setLong(1, newLbId);
                                insertFirewallRules.setInt(2, ipAddressId);
                                insertFirewallRules.setInt(3, Integer.parseInt(sourcePort));
                                insertFirewallRules.setInt(4, Integer.parseInt(sourcePort));
                                insertFirewallRules.setString(5, "tcp");
                                insertFirewallRules.setLong(6, accountId);
                                insertFirewallRules.setLong(7, domainId);
                                insertFirewallRules.setLong(8, networkId);
                                insertFirewallRules.setString(9, UUID.randomUUID().toString());
                                insertFirewallRules.executeUpdate();
                            }
                            s_logger.trace("firewall_rules table is updated as a part of LB rules upgrade");
                        }
                    }


                    // update load_balancing_rules
                    s_logger.trace("Updating load_balancing_rules table as a part of LB rules upgrade...");
                    try (PreparedStatement insertLoadBalancer = conn.prepareStatement("INSERT INTO load_balancing_rules VALUES (?,      ?,      NULL,      ?,       ?,      ?)");) {
                        insertLoadBalancer.setLong(1, newLbId);
                        insertLoadBalancer.setString(2, name);
                        insertLoadBalancer.setInt(3, Integer.parseInt(destPort));
                        insertLoadBalancer.setInt(4, Integer.parseInt(destPort));
                        insertLoadBalancer.setString(5, algorithm);
                        insertLoadBalancer.executeUpdate();
                    }
                    s_logger.trace("load_balancing_rules table is updated as a part of LB rules upgrade");

                    // update load_balancer_vm_map table
                    s_logger.trace("Updating load_balancer_vm_map table as a part of LB rules upgrade...");
                    try (
                            PreparedStatement selectInstance = conn.prepareStatement("SELECT instance_id FROM load_balancer_vm_map WHERE load_balancer_id=?");
                        ) {
                        selectInstance.setLong(1, originalLbId);
                        try (ResultSet selectedInstance = selectInstance.executeQuery();) {
                            ArrayList<Object[]> lbMaps = new ArrayList<Object[]>();
                            while (selectedInstance.next()) {
                                Object[] lbMap = new Object[10];
                                lbMap[0] = selectedInstance.getLong(1); // instanceId
                                lbMaps.add(lbMap);
                            }
                        }
                    }

                    try (PreparedStatement updateLoadBalancer = conn.prepareStatement("UPDATE load_balancer_vm_map SET load_balancer_id=? WHERE load_balancer_id=?");) {
                        updateLoadBalancer.setLong(1, newLbId);
                        updateLoadBalancer.setLong(2, originalLbId);
                        updateLoadBalancer.executeUpdate();
                    }
                    s_logger.trace("load_balancer_vm_map table is updated as a part of LB rules upgrade");
                }
            }
            s_logger.debug("LB rules are upgraded");
        } catch (SQLException e) {
            throw new CloudRuntimeException("Can't update LB rules ", e);
        }
    }

};