//,temp,Upgrade2214to30.java,115,370,temp,Upgrade302to40.java,150,384
//,3
public class xxx {
    private void setupPhysicalNetworks(Connection conn) {
        /**
         * for each zone:
         * add a p.network, use zone.vnet and zone.type
         * add default traffic types, pnsp and virtual router element in enabled state
         * set p.network.id in op_dc_vnet and vlan and user_ip_address
         * list guest networks for the zone, set p.network.id
         *
         * for cases where network_tags are used for multiple guest networks:
         * - figure out distinct tags
         * - create physical network per tag
         * - create traffic types and set the tag to xen_network_label
         * - add physical network id  to networks, vlan, user_ip_address for networks belonging to this tag
         */
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<PreparedStatement> pstmt2Close = new ArrayList<PreparedStatement>();
        PreparedStatement pstmtUpdate = null;
        try {
            // Load all DataCenters

            String xenPublicLabel = getNetworkLabelFromConfig(conn, "xen.public.network.device");
            String xenPrivateLabel = getNetworkLabelFromConfig(conn, "xen.private.network.device");
            String xenStorageLabel = getNetworkLabelFromConfig(conn, "xen.storage.network.device1");
            String xenGuestLabel = getNetworkLabelFromConfig(conn, "xen.guest.network.device");

            String kvmPublicLabel = getNetworkLabelFromConfig(conn, "kvm.public.network.device");
            String kvmPrivateLabel = getNetworkLabelFromConfig(conn, "kvm.private.network.device");
            String kvmGuestLabel = getNetworkLabelFromConfig(conn, "kvm.guest.network.device");

            String vmwarePublicLabel = getNetworkLabelFromConfig(conn, "vmware.public.vswitch");
            String vmwarePrivateLabel = getNetworkLabelFromConfig(conn, "vmware.private.vswitch");
            String vmwareGuestLabel = getNetworkLabelFromConfig(conn, "vmware.guest.vswitch");

            pstmt = conn.prepareStatement("SELECT id, domain_id, networktype, vnet, name, removed FROM `cloud`.`data_center`");
            pstmt2Close.add(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                long zoneId = rs.getLong(1);
                Long domainId = rs.getLong(2);
                String networkType = rs.getString(3);
                String vnet = rs.getString(4);
                String zoneName = rs.getString(5);
                String removed = rs.getString(6);

                //set uuid for the zone
                String uuid = UUID.randomUUID().toString();
                String updateUuid = "UPDATE `cloud`.`data_center` SET uuid = ? WHERE id = ?";
                pstmtUpdate = conn.prepareStatement(updateUuid);
                pstmtUpdate.setString(1, uuid);
                pstmtUpdate.setLong(2, zoneId);
                pstmtUpdate.executeUpdate();
                pstmtUpdate.close();

                //check if public network needs to be created
                boolean crtPbNtwk = false;
                pstmt = conn.prepareStatement("SELECT * FROM `cloud`.`networks` where traffic_type=\"public\" and data_center_id=?");
                pstmt2Close.add(pstmt);
                pstmt.setLong(1, zoneId);
                ResultSet rs1 = pstmt.executeQuery();
                if (rs1.next()) {
                    crtPbNtwk = true;
                }

                //check if there are multiple guest networks configured using network_tags
                PreparedStatement pstmt2 =
                    conn.prepareStatement("SELECT distinct tag FROM `cloud`.`network_tags` t JOIN `cloud`.`networks` n ON t.network_id = n.id WHERE n.data_center_id = ? and n.removed IS NULL");
                pstmt2Close.add(pstmt2);
                pstmt2.setLong(1, zoneId);
                ResultSet rsTags = pstmt2.executeQuery();
                if (rsTags.next()) {
                    s_logger.debug("Network tags are not empty, might have to create more than one physical network...");
                    //make sure setup does not use guest vnets

                    if (vnet != null) {
                        //check if any vnet is allocated and guest networks are using vnets.
                        PreparedStatement pstmt4 =
                            conn.prepareStatement("SELECT v.* FROM `cloud`.`op_dc_vnet_alloc` v JOIN `cloud`.`networks` n ON CONCAT('vlan://' , v.vnet) = " +
                                "n.broadcast_uri WHERE v.taken IS NOT NULL AND v.data_center_id = ? AND n.removed IS NULL");
                        pstmt2Close.add(pstmt4);
                        pstmt4.setLong(1, zoneId);
                        ResultSet rsVNet = pstmt4.executeQuery();

                        if (rsVNet.next()) {
                            String message = "Cannot upgrade. Your setup has multiple Physical Networks and is using guest "
                                + "Vnet that is assigned wrongly. To upgrade, first correct the setup by doing the following: \n"
                                + "1. Please rollback to your 2.2.14 setup\n"
                                + "2. Please stop all VMs using isolated(virtual) networks through CloudStack\n"
                                + "3. Run following query to find if any networks still have nics allocated:\n\t"
                                + "a) check if any virtual guest networks still have allocated nics by running:\n\t"
                                + "SELECT DISTINCT op.id from `cloud`.`op_networks` op JOIN `cloud`.`networks` n on "
                                + "op.id=n.id WHERE nics_count != 0 AND guest_type = 'Virtual';\n\t"
                                + "b) If this returns any networkd ids, then ensure that all VMs are stopped, no new VM is being started, and then shutdown management server\n\t"
                                + "c) Clean up the nics count for the 'virtual' network id's returned in step (a) by running this:\n\t"
                                + "UPDATE `cloud`.`op_networks` SET nics_count = 0 WHERE  id = <enter id of virtual network>\n\t"
                                + "d) Restart management server and wait for all networks to shutdown. [Networks shutdown will be "
                                + "determined by network.gc.interval and network.gc.wait seconds] \n"
                                + "4. Please ensure all networks are shutdown and all guest Vnet's are free.\n"
                                + "5. Run upgrade. This will allocate all your guest vnet range to first physical network.  \n"
                                + "6. Reconfigure the vnet ranges for each physical network as desired by using updatePhysicalNetwork API \n"
                                + "7. Start all your VMs";

                            s_logger.error(message);

                            throw new CloudRuntimeException(
                                "Cannot upgrade this setup since it uses guest vnet and will have multiple physical networks. Please check the logs for details on how to proceed");
                        }
                        rsVNet.close();

                        //Clean up any vnets that have no live networks/nics
                        pstmt4 =
                            conn.prepareStatement("SELECT v.id, v.vnet, v.reservation_id FROM `cloud`.`op_dc_vnet_alloc` v LEFT JOIN networks n ON CONCAT('vlan://' , v.vnet) = n.broadcast_uri WHERE v.taken IS NOT NULL AND v.data_center_id = ? AND n.broadcast_uri IS NULL AND n.removed IS NULL");
                        pstmt2Close.add(pstmt4);
                        pstmt4.setLong(1, zoneId);
                        rsVNet = pstmt4.executeQuery();
                        while (rsVNet.next()) {
                            Long vnet_id = rsVNet.getLong(1);
                            String vnetValue = rsVNet.getString(2);
                            String reservationId = rsVNet.getString(3);
                            //does this vnet have any nic associated?
                            PreparedStatement pstmt5 = conn.prepareStatement("SELECT id, instance_id FROM `cloud`.`nics` where broadcast_uri = ? and removed IS NULL");
                            pstmt2Close.add(pstmt5);
                            String uri = "vlan://" + vnetValue;
                            pstmt5.setString(1, uri);
                            ResultSet rsNic = pstmt5.executeQuery();
                            Long nic_id = rsNic.getLong(1);
                            Long instance_id = rsNic.getLong(2);
                            if (rsNic.next()) {
                                throw new CloudRuntimeException("Cannot upgrade. Please cleanup the guest vnet: " + vnetValue + " , it is being used by nic_id: " +
                                    nic_id + " , instance_id: " + instance_id);
                            }

                            //free this vnet
                            String freeVnet = "UPDATE `cloud`.`op_dc_vnet_alloc` SET account_id = NULL, taken = NULL, reservation_id = NULL WHERE id = ?";
                            pstmtUpdate = conn.prepareStatement(freeVnet);
                            pstmtUpdate.setLong(1, vnet_id);
                            pstmtUpdate.executeUpdate();
                            pstmtUpdate.close();
                        }
                    }

                    boolean isFirstPhysicalNtwk = true;
                    do {
                        //create one physical network per tag
                        String guestNetworkTag = rsTags.getString(1);
                        long physicalNetworkId = addPhysicalNetworkToZone(conn, zoneId, zoneName, networkType, (isFirstPhysicalNtwk) ? vnet : null, domainId);
                        //add Traffic types
                        if (isFirstPhysicalNtwk) {
                            if (crtPbNtwk) {
                                addTrafficType(conn, physicalNetworkId, "Public", xenPublicLabel, kvmPublicLabel, vmwarePublicLabel);
                            } else {
                                s_logger.debug("Skip adding public traffic type to zone id=" + zoneId);
                            }
                            addTrafficType(conn, physicalNetworkId, "Management", xenPrivateLabel, kvmPrivateLabel, vmwarePrivateLabel);
                            addTrafficType(conn, physicalNetworkId, "Storage", xenStorageLabel, null, null);
                        }
                        addTrafficType(conn, physicalNetworkId, "Guest", guestNetworkTag, kvmGuestLabel, vmwareGuestLabel);
                        addDefaultVRProvider(conn, physicalNetworkId, zoneId);
                        addDefaultSGProvider(conn, physicalNetworkId, zoneId, networkType, false);
                        //for all networks with this tag, add physical_network_id

                        PreparedStatement pstmt3 = conn.prepareStatement("SELECT network_id FROM `cloud`.`network_tags` where tag= ?");
                        pstmt3.setString(1,guestNetworkTag);
                        ResultSet rsNet = pstmt3.executeQuery();
                        s_logger.debug("Adding PhysicalNetwork to VLAN");
                        s_logger.debug("Adding PhysicalNetwork to user_ip_address");
                        s_logger.debug("Adding PhysicalNetwork to networks");
                        while (rsNet.next()) {
                            Long networkId = rsNet.getLong(1);
                            addPhysicalNtwk_To_Ntwk_IP_Vlan(conn, physicalNetworkId, networkId);
                        }
                        pstmt3.close();

                        // add the reference to this physical network for the default public network entries in vlan / user_ip_address tables
                        // add first physicalNetworkId to op_dc_vnet_alloc for this zone - just a placeholder since direct networking dont need this
                        if (isFirstPhysicalNtwk) {
                            s_logger.debug("Adding PhysicalNetwork to default Public network entries in vlan and user_ip_address");
                            pstmt3 = conn.prepareStatement("SELECT id FROM `cloud`.`networks` where traffic_type = 'Public' and data_center_id = " + zoneId);
                            ResultSet rsPubNet = pstmt3.executeQuery();
                            if (rsPubNet.next()) {
                                Long publicNetworkId = rsPubNet.getLong(1);
                                addPhysicalNtwk_To_Ntwk_IP_Vlan(conn, physicalNetworkId, publicNetworkId);
                            }
                            pstmt3.close();

                            s_logger.debug("Adding PhysicalNetwork to op_dc_vnet_alloc");
                            String updateVnet = "UPDATE `cloud`.`op_dc_vnet_alloc` SET physical_network_id = " + physicalNetworkId + " WHERE data_center_id = " + zoneId;
                            pstmtUpdate = conn.prepareStatement(updateVnet);
                            pstmtUpdate.executeUpdate();
                            pstmtUpdate.close();
                        }

                        isFirstPhysicalNtwk = false;
                    } while (rsTags.next());
                    pstmt2.close();
                } else {
                    //default to one physical network
                    long physicalNetworkId = addPhysicalNetworkToZone(conn, zoneId, zoneName, networkType, vnet, domainId);
                    // add traffic types
                    if (crtPbNtwk) {
                        addTrafficType(conn, physicalNetworkId, "Public", xenPublicLabel, kvmPublicLabel, vmwarePublicLabel);
                    } else {
                        s_logger.debug("Skip adding public traffic type to zone id=" + zoneId);
                    }
                    addTrafficType(conn, physicalNetworkId, "Management", xenPrivateLabel, kvmPrivateLabel, vmwarePrivateLabel);
                    addTrafficType(conn, physicalNetworkId, "Storage", xenStorageLabel, null, null);
                    addTrafficType(conn, physicalNetworkId, "Guest", xenGuestLabel, kvmGuestLabel, vmwareGuestLabel);
                    addDefaultVRProvider(conn, physicalNetworkId, zoneId);
                    addDefaultSGProvider(conn, physicalNetworkId, zoneId, networkType, false);

                    // add physicalNetworkId to op_dc_vnet_alloc for this zone
                    s_logger.debug("Adding PhysicalNetwork to op_dc_vnet_alloc");
                    String updateVnet = "UPDATE `cloud`.`op_dc_vnet_alloc` SET physical_network_id = " + physicalNetworkId + " WHERE data_center_id = " + zoneId;
                    pstmtUpdate = conn.prepareStatement(updateVnet);
                    pstmtUpdate.executeUpdate();
                    pstmtUpdate.close();

                    // add physicalNetworkId to vlan for this zone
                    s_logger.debug("Adding PhysicalNetwork to VLAN");
                    String updateVLAN = "UPDATE `cloud`.`vlan` SET physical_network_id = " + physicalNetworkId + " WHERE data_center_id = " + zoneId;
                    pstmtUpdate = conn.prepareStatement(updateVLAN);
                    pstmtUpdate.executeUpdate();
                    pstmtUpdate.close();

                    // add physicalNetworkId to user_ip_address for this zone
                    s_logger.debug("Adding PhysicalNetwork to user_ip_address");
                    String updateUsrIp = "UPDATE `cloud`.`user_ip_address` SET physical_network_id = " + physicalNetworkId + " WHERE data_center_id = " + zoneId;
                    pstmtUpdate = conn.prepareStatement(updateUsrIp);
                    pstmtUpdate.executeUpdate();
                    pstmtUpdate.close();

                    // add physicalNetworkId to guest networks for this zone
                    s_logger.debug("Adding PhysicalNetwork to networks");
                    String updateNet =
                        "UPDATE `cloud`.`networks` SET physical_network_id = " + physicalNetworkId + " WHERE data_center_id = " + zoneId + " AND traffic_type = 'Guest'";
                    pstmtUpdate = conn.prepareStatement(updateNet);
                    pstmtUpdate.executeUpdate();
                    pstmtUpdate.close();

                    //mark this physical network as removed if the zone is removed.
                    if (removed != null) {
                        pstmtUpdate = conn.prepareStatement("UPDATE `cloud`.`physical_network` SET removed = now() WHERE id = ?");
                        pstmtUpdate.setLong(1, physicalNetworkId);
                        pstmtUpdate.executeUpdate();
                        pstmtUpdate.close();
                    }
                }

            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Exception while adding PhysicalNetworks", e);
        } finally {
            TransactionLegacy.closePstmts(pstmt2Close);
        }

    }

};