//,temp,Upgrade218to22.java,366,448,temp,Upgrade218to22.java,275,364
//,3
public class xxx {
    protected void upgradeConsoleProxy(Connection conn, long dcId, long cpId, long publicNetworkId, long managementNetworkId, long controlNetworkId, String zoneType)
        throws SQLException {
        s_logger.debug("Upgrading cp" + cpId);
        try (PreparedStatement pstmt =
            conn.prepareStatement("SELECT vm_instance.id, vm_instance.state, vm_instance.private_mac_address, vm_instance.private_ip_address, vm_instance.private_netmask, console_proxy.public_mac_address, console_proxy.public_ip_address, console_proxy.public_netmask, console_proxy.guest_mac_address, console_proxy.guest_ip_address, console_proxy.guest_netmask, console_proxy.gateway, vm_instance.type FROM vm_instance INNER JOIN console_proxy ON vm_instance.id=console_proxy.id WHERE vm_instance.removed is NULL AND vm_instance.id=?");) {
            pstmt.setLong(1, cpId);
            try (ResultSet rs = pstmt.executeQuery();) {

                if (!rs.next()) {
                    throw new CloudRuntimeException("Unable to find cp " + cpId);
                }

//                long id = rs.getLong(1);
                String state = rs.getString(2);
                boolean running = state.equals("Running") | state.equals("Starting") | state.equals("Stopping");
                String privateMac = rs.getString(3);
                String privateIp = rs.getString(4);
                String privateNetmask = rs.getString(5);
                String publicMac = rs.getString(6);
                String publicIp = rs.getString(7);
                String publicNetmask = rs.getString(8);
                String guestMac = rs.getString(9);
                String guestIp = rs.getString(10);
                String guestNetmask = rs.getString(11);
                String gateway = rs.getString(12);
//                String type = rs.getString(13);
                try (
                        PreparedStatement selectHost =
                        conn.prepareStatement("SELECT host_pod_ref.gateway from host_pod_ref INNER JOIN vm_instance ON vm_instance.pod_id=host_pod_ref.id WHERE vm_instance.id=?");
                    ) {
                    selectHost.setLong(1, cpId);
                    try (ResultSet hostResult = selectHost.executeQuery();) {

                        if (!hostResult.next()) {
                            throw new CloudRuntimeException("Unable to find cp " + cpId);
                        }

                        String podGateway = hostResult.getString(1);
                        try (PreparedStatement selectVlan = conn.prepareStatement("SELECT v.vlan_id from vlan v, user_ip_address u where v.id=u.vlan_db_id and u.public_ip_address=?");) {
                            selectVlan.setString(1, publicIp);
                            try (ResultSet vlanResult = selectVlan.executeQuery();) {

                                String publicVlan = null;
                                while (vlanResult.next()) {
                                    publicVlan = vlanResult.getString(1);
                                }
                                if (zoneType.equalsIgnoreCase("Basic")) {
                                    insertNic(conn, publicNetworkId, cpId, running, publicMac, publicIp, publicNetmask, "Create", gateway, publicVlan, "DirectPodBasedNetworkGuru", true, 2,
                                        "Dhcp", null);
                                } else {
                                    insertNic(conn, publicNetworkId, cpId, running, publicMac, publicIp, publicNetmask, "Create", gateway, publicVlan, "PublicNetworkGuru", true, 2, "Static",
                                        null);
                                }

                                long controlNicId =
                                    insertNic(conn, controlNetworkId, cpId, running, guestMac, guestIp, guestNetmask, "Start", "169.254.0.1", null, "ControlNetworkGuru", false, 0, "Static",
                                        guestIp != null ? (cpId + guestIp) : null);
                                if (guestIp != null) {
                                    try (PreparedStatement update = conn.prepareStatement("UPDATE op_dc_link_local_ip_address_alloc SET instance_id=? WHERE ip_address=? AND data_center_id=?");) {
                                        update.setLong(1, controlNicId);
                                        update.setString(2, guestIp);
                                        update.setLong(3, dcId);
                                        update.executeUpdate();
                                    }
                                }
                                long mgmtNicId =
                                    insertNic(conn, managementNetworkId, cpId, running, privateMac, privateIp, privateNetmask, "Start", podGateway, null, "PodBasedNetworkGuru", false, 1,
                                        "Static", privateIp != null ? (cpId + privateIp) : null);
                                if (privateIp != null) {
                                    try (PreparedStatement update = conn.prepareStatement("UPDATE op_dc_ip_address_alloc SET instance_id=? WHERE ip_address=? AND data_center_id=?");) {
                                        update.setLong(1, mgmtNicId);
                                        update.setString(2, privateIp);
                                        update.setLong(3, dcId);
                                        update.executeUpdate();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

};