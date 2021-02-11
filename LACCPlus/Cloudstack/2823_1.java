//,temp,Upgrade303to304.java,103,310,temp,Upgrade302to40.java,150,384
//,3
public class xxx {
    private void correctMultiplePhysicaNetworkSetups(Connection conn) {

        try (
                PreparedStatement pstmtZone = conn.prepareStatement("SELECT id, domain_id, networktype, name, uuid FROM `cloud`.`data_center`");
                ResultSet rsZone = pstmtZone.executeQuery();
                PreparedStatement pstmt_count_networks = conn.prepareStatement("SELECT count(n.id) FROM networks n WHERE n.physical_network_id IS NULL AND n.traffic_type = 'Guest' and n.data_center_id = ? and n.removed is null");
                PreparedStatement pstmtUpdate = conn.prepareStatement("UPDATE `cloud`.`data_center` SET uuid = ? WHERE id = ?");
                PreparedStatement pstmt_count_traffic_types_and_labels =
                        conn.prepareStatement("SELECT count(*) FROM `cloud`.`physical_network_traffic_types` pntt JOIN `cloud`.`physical_network` pn ON pntt.physical_network_id = pn.id WHERE pntt.traffic_type ='Guest' AND pn.data_center_id = ? AND pntt.xen_network_label = ?");
                PreparedStatement pstmt_network_id =
                        conn.prepareStatement("SELECT n.id FROM networks n WHERE n.physical_network_id IS NULL AND n.traffic_type = 'Guest' and n.data_center_id = ? and n.removed is null");
                PreparedStatement pstmt_count_traffic_types =
                        conn.prepareStatement("SELECT count(*) FROM `cloud`.`physical_network_traffic_types` pntt JOIN `cloud`.`physical_network` pn ON pntt.physical_network_id = pn.id WHERE pntt.traffic_type ='Guest' and pn.data_center_id = ?");
                PreparedStatement pstmt_select_vnets_on_different_physical_net =
                        conn.prepareStatement("SELECT v.id, v.vnet, v.reservation_id, v.physical_network_id as vpid, n.id, n.physical_network_id as npid FROM `cloud`.`op_dc_vnet_alloc` v JOIN `cloud`.`networks` n ON CONCAT('vlan://' , v.vnet) = n.broadcast_uri WHERE v.taken IS NOT NULL AND v.data_center_id = ? AND n.removed IS NULL AND v.physical_network_id !=  n.physical_network_id");
                PreparedStatement pstmt_select_vnets =
                        conn.prepareStatement("SELECT v.id, v.vnet, v.reservation_id FROM `cloud`.`op_dc_vnet_alloc` v LEFT JOIN networks n ON CONCAT('vlan://' , v.vnet) = n.broadcast_uri WHERE v.taken IS NOT NULL AND v.data_center_id = ? AND n.broadcast_uri IS NULL AND n.removed IS NULL");
                PreparedStatement pstmt_select_nic_ids = conn.prepareStatement("SELECT id, instance_id FROM `cloud`.`nics` where broadcast_uri = ? and removed IS NULL");
                PreparedStatement pstmt_physical_network_tags =
                        conn.prepareStatement("SELECT pn.id as pid , ptag.tag as tag FROM `cloud`.`physical_network` pn LEFT JOIN `cloud`.`physical_network_tags` ptag ON pn.id = ptag.physical_network_id where pn.data_center_id = ?");
                PreparedStatement pstmt_insert_physical_networkTags = conn.prepareStatement("INSERT INTO `cloud`.`physical_network_tags`(tag, physical_network_id) VALUES( ?, ? )");
                PreparedStatement pstmt_select_distict_netoffers =
                        conn.prepareStatement("SELECT distinct network_offering_id FROM `cloud`.`networks` where traffic_type= 'Guest' and physical_network_id = ? and removed is null");
                PreparedStatement pstmt_update_vnet_alloc = conn.prepareStatement("UPDATE `cloud`.`op_dc_vnet_alloc` SET account_id = NULL, taken = NULL, reservation_id = NULL WHERE id = ?");
                ) {

            //check if multiple physical networks with 'Guest' Traffic types are present
            //Yes:
            //1) check if there are guest networks without tags, if yes then add a new physical network with default tag for them
            //2) Check if there are physical network tags present
            //No: Add unique tag to each physical network
            //3) Get all guest networks unique network offering id's

            //Clone each for each physical network and add the tag.
            //add ntwk service map entries
            //update all guest networks of 1 physical network having this offering id to this new offering id

            while (rsZone.next()) {
                long zoneId = rsZone.getLong(1);
                Long domainId = rsZone.getLong(2);
                String networkType = rsZone.getString(3);
                String zoneName = rsZone.getString(4);
                String uuid = rsZone.getString(5);

                if (uuid == null) {
                    uuid = UUID.randomUUID().toString();
                    pstmtUpdate.setString(1, uuid);
                    pstmtUpdate.setLong(2, zoneId);
                    pstmtUpdate.executeUpdate();
                }

                //check if any networks were untagged and remaining to be mapped to a physical network
                pstmt_count_networks.setLong(1, zoneId);
                try (ResultSet rsNetworks = pstmt_count_networks.executeQuery();) {
                    if (rsNetworks.next()) {
                        Long count = rsNetworks.getLong(1);
                        if (count > 0) {
                            // find the default tag to use from global config or use 'cloud-private'
                            String xenGuestLabel = getNetworkLabelFromConfig(conn, "xen.guest.network.device");
                            //Decrypt this value.
                            xenGuestLabel = DBEncryptionUtil.decrypt(xenGuestLabel);

                            //make sure that no physical network with this traffic label already exists. if yes, error out.
                            if (xenGuestLabel != null) {
                                pstmt_count_traffic_types_and_labels.setLong(1, zoneId);
                                pstmt_count_traffic_types_and_labels.setString(2, xenGuestLabel);
                                try (ResultSet rsSameLabel = pstmt_count_traffic_types_and_labels.executeQuery();) {

                                    if (rsSameLabel.next()) {
                                        Long sameLabelcount = rsSameLabel.getLong(1);
                                        if (sameLabelcount > 0) {
                                            s_logger.error("There are untagged networks for which we need to add a physical network with Xen traffic label = 'xen.guest.network.device' config value, which is: " +
                                                    xenGuestLabel);
                                            s_logger.error("However already there are " + sameLabelcount + " physical networks setup with same traffic label, cannot upgrade");
                                            throw new CloudRuntimeException("Cannot upgrade this setup since a physical network with same traffic label: " + xenGuestLabel +
                                                    " already exists, Please check logs and contact Support.");
                                        }
                                    }
                                }
                            }
                            //Create a physical network with guest traffic type and this tag
                            long physicalNetworkId = addPhysicalNetworkToZone(conn, zoneId, zoneName, networkType, null, domainId);
                            addTrafficType(conn, physicalNetworkId, "Guest", xenGuestLabel, null, null);
                            addDefaultVRProvider(conn, physicalNetworkId, zoneId);
                            addDefaultSGProvider(conn, physicalNetworkId, zoneId, networkType, true);

                            pstmt_network_id.setLong(1, zoneId);
                            try (ResultSet rsNet = pstmt_network_id.executeQuery();) {
                                s_logger.debug("Adding PhysicalNetwork to VLAN");
                                s_logger.debug("Adding PhysicalNetwork to user_ip_address");
                                s_logger.debug("Adding PhysicalNetwork to networks");
                                while (rsNet.next()) {
                                    Long networkId = rsNet.getLong(1);
                                    addPhysicalNtwk_To_Ntwk_IP_Vlan(conn, physicalNetworkId, networkId);
                                }
                            }
                        }
                    }
                }

                boolean multiplePhysicalNetworks = false;

                pstmt_count_traffic_types.setLong(1, zoneId);
                try (ResultSet rs = pstmt_count_traffic_types.executeQuery();) {
                    if (rs.next()) {
                        Long count = rs.getLong(1);
                        if (count > 1) {
                            s_logger.debug("There are " + count + " physical networks setup");
                            multiplePhysicalNetworks = true;
                        }
                    }
                }

                if (multiplePhysicalNetworks) {
                    //check if guest vnet is wrongly configured by earlier upgrade. If yes error out
                    //check if any vnet is allocated and guest networks are using vnet But the physical network id does not match on the vnet and guest network.
                    pstmt_select_vnets_on_different_physical_net.setLong(1, zoneId);
                    try (ResultSet rsVNet = pstmt_select_vnets_on_different_physical_net.executeQuery();) {
                        if (rsVNet.next()) {
                            String vnet = rsVNet.getString(2);
                            String networkId = rsVNet.getString(5);
                            String vpid = rsVNet.getString(4);
                            String npid = rsVNet.getString(6);
                            s_logger.error("Guest Vnet assignment is set wrongly . Cannot upgrade until that is corrected. Example- Vnet: " + vnet +
                                    " has physical network id: " + vpid + " ,but the guest network: " + networkId + " that uses it has physical network id: " + npid);

                            String message = "Cannot upgrade. Your setup has multiple Physical Networks and is using guest Vnet that is assigned wrongly. "
                                    + "To upgrade, first correct the setup by doing the following: \n"
                                    + "1. Please rollback to your 2.2.14 setup\n"
                                    + "2. Please stop all VMs using isolated(virtual) networks through CloudStack\n"
                                    + "3. Run following query to find if any networks still have nics allocated:\n\t"
                                    + "a) check if any virtual guest networks still have allocated nics by running:\n\t"
                                    + "SELECT DISTINCT op.id from `cloud`.`op_networks` op JOIN `cloud`.`networks` n on op.id=n.id WHERE nics_count != 0 AND guest_type = 'Virtual';\n\t"
                                    + "b) If this returns any networkd ids, then ensure that all VMs are stopped, no new VM is being started, and then shutdown management server\n\t"
                                    + "c) Clean up the nics count for the 'virtual' network id's returned in step (a) by running this:\n\t"
                                    + "UPDATE `cloud`.`op_networks` SET nics_count = 0 WHERE  id = <enter id of virtual network>\n\t"
                                    + "d) Restart management server and wait for all networks to shutdown. "
                                    + "[Networks shutdown will be determined by network.gc.interval and network.gc.wait seconds] \n"
                                    + "4. Please ensure all networks are shutdown and all guest Vnet's are free.\n"
                                    + "5. Run upgrade. This will allocate all your guest vnet range to first physical network.  \n"
                                    + "6. Reconfigure the vnet ranges for each physical network as desired by using updatePhysicalNetwork API \n" + "7. Start all your VMs";

                            s_logger.error(message);
                            throw new CloudRuntimeException("Cannot upgrade this setup since Guest Vnet assignment to the multiple physical networks " +
                                    "is incorrect. Please check the logs for details on how to proceed");

                        }
                    }

                    //Clean up any vnets that have no live networks/nics
                    pstmt_select_vnets.setLong(1, zoneId);
                    try (ResultSet rsVNet = pstmt_select_vnets.executeQuery();) {
                        while (rsVNet.next()) {
                            Long vnet_id = rsVNet.getLong(1);
                            String vnetValue = rsVNet.getString(2);
                            // third result parameter is never used: String reservationId = rsVNet.getString(3);
                            //does this vnet have any nic associated?
                            String uri = "vlan://" + vnetValue;
                            pstmt_select_nic_ids.setString(1, uri);
                            try (ResultSet rsNic = pstmt_select_nic_ids.executeQuery();) {
                                Long nic_id = rsNic.getLong(1);
                                Long instance_id = rsNic.getLong(2);
                                if (rsNic.next()) {
                                    throw new CloudRuntimeException("Cannot upgrade. Please cleanup the guest vnet: " + vnetValue + " , it is being used by nic_id: " + nic_id +
                                            " , instance_id: " + instance_id);
                                }
                            }
                            //free this vnet
                            pstmt_update_vnet_alloc.setLong(1, vnet_id);
                            pstmt_update_vnet_alloc.executeUpdate();
                            pstmt_update_vnet_alloc.close();
                        }
                    }

                    //add tags to the physical networks if not present and clone offerings

                    pstmt_physical_network_tags.setLong(1, zoneId);
                    try (ResultSet rs_network_count = pstmt_physical_network_tags.executeQuery();) {
                        while (rs_network_count.next()) {
                            long physicalNetworkId = rs_network_count.getLong("pid");
                            String tag = rs_network_count.getString("tag");
                            if (tag == null) {
                                //need to add unique tag
                                String newTag = "pNtwk-tag-" + physicalNetworkId;

                                pstmt_insert_physical_networkTags.setString(1, newTag);
                                pstmt_insert_physical_networkTags.setLong(2, physicalNetworkId);
                                pstmt_insert_physical_networkTags.executeUpdate();
                                pstmt_insert_physical_networkTags.close();

                                //clone offerings and tag them with this new tag, if there are any guest networks for this physical network
                                pstmt_select_distict_netoffers.setLong(1, physicalNetworkId);
                                try (ResultSet rs_netoffers = pstmt_select_distict_netoffers.executeQuery();) {
                                    while (rs_netoffers.next()) {
                                        //clone each offering, add new tag, clone offering-svc-map, update guest networks with new offering id
                                        long networkOfferingId = rs_netoffers.getLong(1);
                                        cloneOfferingAndAddTag(conn, networkOfferingId, physicalNetworkId, newTag);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Exception while correcting PhysicalNetwork setup", e);
        }
    }

};