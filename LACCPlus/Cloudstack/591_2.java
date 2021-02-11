//,temp,Upgrade40to41.java,97,185,temp,Upgrade410to420.java,282,374
//,3
public class xxx {
    private void movePrivateZoneToDedicatedResource(Connection conn) {
        String domainName = "";
        try (PreparedStatement sel_dc_dom_id = conn.prepareStatement("SELECT distinct(`domain_id`) FROM `cloud`.`data_center` WHERE `domain_id` IS NOT NULL AND removed IS NULL");) {
            try (ResultSet rs3 = sel_dc_dom_id.executeQuery();) {
                while (rs3.next()) {
                    long domainId = rs3.getLong(1);
                    long affinityGroupId = 0;
                    // create or find an affinity group for this domain of type
                    // 'ExplicitDedication'
                    try (PreparedStatement sel_aff_grp_pstmt =
                                 conn.prepareStatement("SELECT affinity_group.id FROM `cloud`.`affinity_group` INNER JOIN `cloud`.`affinity_group_domain_map` ON affinity_group.id=affinity_group_domain_map.affinity_group_id WHERE affinity_group.type = 'ExplicitDedication' AND affinity_group.acl_type = 'Domain'  AND  (affinity_group_domain_map.domain_id = ?)");) {
                        sel_aff_grp_pstmt.setLong(1, domainId);
                        try (ResultSet rs2 = sel_aff_grp_pstmt.executeQuery();) {
                            if (rs2.next()) {
                                // group exists, use it
                                affinityGroupId = rs2.getLong(1);
                            } else {
                                // create new group
                                try (PreparedStatement sel_dom_id_pstmt = conn.prepareStatement("SELECT name FROM `cloud`.`domain` where id = ?");) {
                                    sel_dom_id_pstmt.setLong(1, domainId);
                                    try (ResultSet sel_dom_id_res = sel_dom_id_pstmt.executeQuery();) {
                                        if (sel_dom_id_res.next()) {
                                            domainName = sel_dom_id_res.getString(1);
                                        }
                                    }
                                } catch (SQLException e) {
                                    throw new CloudRuntimeException("Exception while Moving private zone information to dedicated resources", e);
                                }
                                // create new domain level group for this domain
                                String type = "ExplicitDedication";
                                String uuid = UUID.randomUUID().toString();
                                String groupName = "DedicatedGrp-domain-" + domainName;
                                s_logger.debug("Adding AffinityGroup of type " + type + " for domain id " + domainId);
                                String sql =
                                        "INSERT INTO `cloud`.`affinity_group` (`name`, `type`, `uuid`, `description`, `domain_id`, `account_id`, `acl_type`) VALUES (?, ?, ?, ?, 1, 1, 'Domain')";
                                try (PreparedStatement insert_pstmt = conn.prepareStatement(sql);) {
                                    insert_pstmt.setString(1, groupName);
                                    insert_pstmt.setString(2, type);
                                    insert_pstmt.setString(3, uuid);
                                    insert_pstmt.setString(4, "dedicated resources group");
                                    insert_pstmt.executeUpdate();
                                    try (PreparedStatement sel_aff_pstmt = conn.prepareStatement("SELECT affinity_group.id FROM `cloud`.`affinity_group` where uuid = ?");) {
                                        sel_aff_pstmt.setString(1, uuid);
                                        try (ResultSet sel_aff_res = sel_aff_pstmt.executeQuery();) {
                                            if (sel_aff_res.next()) {
                                                affinityGroupId = sel_aff_res.getLong(1);
                                            }
                                        } catch (SQLException e) {
                                            throw new CloudRuntimeException("Exception while Moving private zone information to dedicated resources", e);
                                        }
                                    } catch (SQLException e) {
                                        throw new CloudRuntimeException("Exception while Moving private zone information to dedicated resources", e);
                                    }
                                } catch (SQLException e) {
                                    throw new CloudRuntimeException("Exception while Moving private zone information to dedicated resources", e);
                                }
                                // add the domain map
                                String sqlMap = "INSERT INTO `cloud`.`affinity_group_domain_map` (`domain_id`, `affinity_group_id`) VALUES (?, ?)";
                                try (PreparedStatement pstmtUpdate = conn.prepareStatement(sqlMap);) {
                                    pstmtUpdate.setLong(1, domainId);
                                    pstmtUpdate.setLong(2, affinityGroupId);
                                    pstmtUpdate.executeUpdate();
                                } catch (SQLException e) {
                                    throw new CloudRuntimeException("Exception while Moving private zone information to dedicated resources", e);
                                }
                            }
                        } catch (SQLException e) {
                            throw new CloudRuntimeException("Exception while Moving private zone information to dedicated resources", e);
                        }
                    } catch (SQLException e) {
                        throw new CloudRuntimeException("Exception while Moving private zone information to dedicated resources", e);
                    }
                    try (PreparedStatement sel_pstmt = conn.prepareStatement("SELECT `id` FROM `cloud`.`data_center` WHERE `domain_id` = ? AND removed IS NULL");) {
                        sel_pstmt.setLong(1, domainId);
                        try (ResultSet sel_pstmt_rs = sel_pstmt.executeQuery();) {
                            while (sel_pstmt_rs.next()) {
                                long zoneId = sel_pstmt_rs.getLong(1);
                                dedicateZone(conn, zoneId, domainId, affinityGroupId);
                            }
                        } catch (SQLException e) {
                            throw new CloudRuntimeException("Exception while Moving private zone information to dedicated resources", e);
                        }
                    } catch (SQLException e) {
                        throw new CloudRuntimeException("Exception while Moving private zone information to dedicated resources", e);
                    }
                }
            } catch (SQLException e) {
                throw new CloudRuntimeException("Exception while Moving private zone information to dedicated resources", e);
            }
        }catch (SQLException e) {
            throw new CloudRuntimeException("Exception while Moving private zone information to dedicated resources", e);
        }
    }

};