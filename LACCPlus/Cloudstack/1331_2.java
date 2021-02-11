//,temp,Upgrade218to22.java,366,448,temp,Upgrade218to22.java,275,364
//,3
public class xxx {
    protected void upgradeSsvm(Connection conn, long dataCenterId, long publicNetworkId, long managementNetworkId, long controlNetworkId, String zoneType)
        throws SQLException {
        s_logger.debug("Upgrading ssvm in " + dataCenterId);
        //select instance
        try (
                PreparedStatement selectInstance =
            conn.prepareStatement("SELECT vm_instance.id, vm_instance.state, vm_instance.private_mac_address, vm_instance.private_ip_address, vm_instance.private_netmask, secondary_storage_vm.public_mac_address, secondary_storage_vm.public_ip_address, secondary_storage_vm.public_netmask, secondary_storage_vm.guest_mac_address, secondary_storage_vm.guest_ip_address, secondary_storage_vm.guest_netmask, secondary_storage_vm.gateway, vm_instance.type FROM vm_instance INNER JOIN secondary_storage_vm ON vm_instance.id=secondary_storage_vm.id WHERE vm_instance.removed is NULL AND vm_instance.data_center_id=? AND vm_instance.type='SecondaryStorageVm'");
            ) {
            selectInstance.setLong(1, dataCenterId);
            try (ResultSet instanceResult = selectInstance.executeQuery();) {

                if (!instanceResult.next()) {
                    s_logger.debug("Unable to find ssvm in data center " + dataCenterId);
                    return;
                }

                long ssvmId = instanceResult.getLong(1);
                String state = instanceResult.getString(2);
                boolean running = state.equals("Running") | state.equals("Starting") | state.equals("Stopping");
                String privateMac = instanceResult.getString(3);
                String privateIp = instanceResult.getString(4);
                String privateNetmask = instanceResult.getString(5);
                String publicMac = instanceResult.getString(6);
                String publicIp = instanceResult.getString(7);
                String publicNetmask = instanceResult.getString(8);
                String guestMac = instanceResult.getString(9);
                String guestIp = instanceResult.getString(10);
                String guestNetmask = instanceResult.getString(11);
                String gateway = instanceResult.getString(12);
//                String type = instanceResult.getString(13);
                // select host
                try (PreparedStatement selectHost =
                    conn.prepareStatement("SELECT host_pod_ref.gateway from host_pod_ref INNER JOIN vm_instance ON vm_instance.pod_id=host_pod_ref.id WHERE vm_instance.removed is NULL AND vm_instance.data_center_id=? AND vm_instance.type='SecondaryStorageVm'");) {
                    selectHost.setLong(1, dataCenterId);
                    try (ResultSet hostResult = selectHost.executeQuery();) {

                        if (!hostResult.next()) {
                            s_logger.debug("Unable to find ssvm in data center " + dataCenterId);
                            return;
                        }

                        String podGateway = hostResult.getString(1);
                        // select vlan
                        try (PreparedStatement selectVlan = conn.prepareStatement("SELECT v.vlan_id from vlan v, user_ip_address u where v.id=u.vlan_db_id and u.public_ip_address=?");) {
                            selectVlan.setString(1, publicIp);
                            try (ResultSet vlanResult = selectVlan.executeQuery();) {
                                String publicVlan = null;
                                while (vlanResult.next()) {
                                    publicVlan = vlanResult.getString(1);
                                }
                                if (zoneType.equalsIgnoreCase("Basic")) {
                                    insertNic(conn, publicNetworkId, ssvmId, running, publicMac, publicIp, publicNetmask, "Create", gateway, publicVlan, "DirectPodBasedNetworkGuru", true, 2,
                                        "Dhcp", null);

                                } else {
                                    insertNic(conn, publicNetworkId, ssvmId, running, publicMac, publicIp, publicNetmask, "Create", gateway, publicVlan, "PublicNetworkGuru", true, 2, "Static",
                                        null);
                                }
                            }
                        }


                        long controlNicId =
                            insertNic(conn, controlNetworkId, ssvmId, running, guestMac, guestIp, guestNetmask, "Start", "169.254.0.1", null, "ControlNetworkGuru", false, 0, "Static",
                                guestIp != null ? (ssvmId + guestIp) : null);
                        if (guestIp != null) {
                            try (PreparedStatement updateLinkLocal = conn.prepareStatement("UPDATE op_dc_link_local_ip_address_alloc SET instance_id=? WHERE ip_address=? AND data_center_id=?");) {
                                updateLinkLocal.setLong(1, controlNicId);
                                updateLinkLocal.setString(2, guestIp);
                                updateLinkLocal.setLong(3, dataCenterId);
                                updateLinkLocal.executeUpdate();
                            }
                        }

                        long mgmtNicId =
                            insertNic(conn, managementNetworkId, ssvmId, running, privateMac, privateIp, privateNetmask, "Start", podGateway, null, "PodBasedNetworkGuru", false, 1,
                                "Static", null);
                        if (privateIp != null) {
                            try (PreparedStatement updateIp = conn.prepareStatement("UPDATE op_dc_ip_address_alloc SET instance_id=? WHERE ip_address=? AND data_center_id=?");) {
                                updateIp.setLong(1, mgmtNicId);
                                updateIp.setString(2, privateIp);
                                updateIp.setLong(3, dataCenterId);
                                updateIp.executeUpdate();
                            }
                        }
                    }
                }
            }
        }
    }

};