//,temp,Upgrade218to22.java,275,364,temp,Upgrade218to22.java,213,273
//,3
public class xxx {
    protected void upgradeDomR(Connection conn, long dcId, long domrId, Long publicNetworkId, long guestNetworkId, long controlNetworkId, String zoneType, String vnet)
        throws SQLException {
        s_logger.debug("Upgrading domR" + domrId);
        try (
                PreparedStatement pstmt =
            conn.prepareStatement("SELECT vm_instance.id, vm_instance.state, vm_instance.private_mac_address, vm_instance.private_ip_address, vm_instance.private_netmask, domain_router.public_mac_address, domain_router.public_ip_address, domain_router.public_netmask, domain_router.guest_mac_address, domain_router.guest_ip_address, domain_router.guest_netmask, domain_router.vnet, domain_router.gateway FROM vm_instance INNER JOIN domain_router ON vm_instance.id=domain_router.id WHERE vm_instance.removed is NULL AND vm_instance.id=?");
            ) {
            pstmt.setLong(1, domrId);
            try (ResultSet rs = pstmt.executeQuery();) {

                if (!rs.next()) {
                    throw new CloudRuntimeException("Unable to find router " + domrId);
                }
                // long id = rs.getLong(1);
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
                String gateway = rs.getString(13);
                try (PreparedStatement vlanStatement = conn.prepareStatement("SELECT v.vlan_id from vlan v, user_ip_address u where v.id=u.vlan_db_id and u.public_ip_address=?");) {
                    vlanStatement.setString(1, publicIp);
                    try (ResultSet vlanResult = vlanStatement.executeQuery();) {
                        String publicVlan = null;
                        while (vlanResult.next()) {
                            publicVlan = vlanResult.getString(1);
                        }
                        // Control nic is the same for all types of networks
                        long controlNicId =
                            insertNic(conn, controlNetworkId, domrId, running, privateMac, privateIp, privateNetmask, "Start", "169.254.0.1", null, "ControlNetworkGuru", false, 1,
                                "Static", privateIp != null ? (domrId + privateIp) : null);
                        if (privateIp != null) {
                            try (PreparedStatement updateStatement = conn.prepareStatement("UPDATE op_dc_link_local_ip_address_alloc SET instance_id=? WHERE ip_address=? AND data_center_id=?");) {
                                updateStatement.setLong(1, controlNicId);
                                updateStatement.setString(2, privateIp);
                                updateStatement.setLong(3, dcId);
                                updateStatement.executeUpdate();
                            }
                        }
                        if (zoneType.equalsIgnoreCase("Basic")) {
                            insertNic(conn, guestNetworkId, domrId, running, guestMac, guestIp, guestNetmask, "Create", gateway, vnet, "DirectPodBasedNetworkGuru", true, 0, "Dhcp", null);
                        } else if (publicIp != null) {
                            // update virtual domR
                            insertNic(conn, publicNetworkId, domrId, running, publicMac, publicIp, publicNetmask, "Create", gateway, publicVlan, "PublicNetworkGuru", true, 2, "Static",
                                null);
                            insertNic(conn, guestNetworkId, domrId, running, guestMac, guestIp, guestNetmask, "Start", null, vnet, "ExternalGuestNetworkGuru", false, 0, "Dhcp", null);
                        } else {
                            // update direct domR - dhcp case
                            insertNic(conn, guestNetworkId, domrId, running, guestMac, guestIp, guestNetmask, "Create", gateway, vnet, "DirectNetworkGuru", true, 0, "Dhcp", null);
                        }
                    }
                }
            }
        }
    }

};