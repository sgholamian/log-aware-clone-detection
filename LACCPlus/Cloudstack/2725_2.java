//,temp,Upgrade410to420.java,952,1010,temp,Upgrade430to440.java,96,167
//,3
public class xxx {
    private void secondaryIpsAccountAndDomainIdsUpdate(Connection conn) {
        String secondIpsSql = "SELECT id, vmId, network_id, account_id, domain_id, ip4_address FROM `cloud`.`nic_secondary_ips`";

        try (PreparedStatement pstmt = conn.prepareStatement(secondIpsSql);
             ResultSet rs1 = pstmt.executeQuery();
            ) {
            while(rs1.next()) {
                long ipId = rs1.getLong(1);
                long vmId = rs1.getLong(2);
                long networkId = rs1.getLong(3);
                long accountId = rs1.getLong(4);
                long domainId = rs1.getLong(5);
                String ipAddr = rs1.getString(6);

                try(PreparedStatement pstmtVm = conn.prepareStatement("SELECT account_id, domain_id FROM `cloud`.`vm_instance` where id = ?");) {
                    pstmtVm.setLong(1,vmId);

                    try(ResultSet vmRs = pstmtVm.executeQuery();) {

                        if (vmRs.next()) {
                            long vmAccountId = vmRs.getLong(1);
                            long vmDomainId = vmRs.getLong(2);

                            if (vmAccountId != accountId && vmAccountId != domainId) {
                                // update the secondary ip accountid and domainid to vm accountid domainid
                                // check the network type. If network is shared accountid doaminid needs to be updated in
                                // in both nic_secondary_ips table and user_ip_address table

                                try(PreparedStatement pstmtUpdate = conn.prepareStatement("UPDATE `cloud`.`nic_secondary_ips` SET account_id = ?, domain_id= ? WHERE id = ?");) {
                                    pstmtUpdate.setLong(1, vmAccountId);
                                    pstmtUpdate.setLong(2,vmDomainId);
                                    pstmtUpdate.setLong(3,ipId);
                                    pstmtUpdate.executeUpdate();
                                } catch (SQLException e) {
                                    throw new CloudRuntimeException("Exception while updating secondary ip for nic " + ipId, e);
                                }

                                try(PreparedStatement pstmtNw = conn.prepareStatement("SELECT guest_type FROM `cloud`.`networks` where id = ?");) {
                                    pstmtNw.setLong(1,networkId);

                                    try(ResultSet networkRs = pstmtNw.executeQuery();) {
                                        if (networkRs.next()) {
                                            String guesttype = networkRs.getString(1);

                                            if (guesttype.equals(Network.GuestType.Shared.toString())) {
                                                try(PreparedStatement pstmtUpdate = conn.prepareStatement("UPDATE `cloud`.`user_ip_address` SET account_id = ?, domain_id= ? WHERE public_ip_address = ?");) {
                                                    pstmtUpdate.setLong(1,vmAccountId);
                                                    pstmtUpdate.setLong(2,vmDomainId);
                                                    pstmtUpdate.setString(3,ipAddr);
                                                    pstmtUpdate.executeUpdate();
                                                } catch (SQLException e) {
                                                    throw new CloudRuntimeException("Exception while updating public ip  " + ipAddr, e);
                                                }
                                            }
                                        }
                                    } catch (SQLException e) {
                                        throw new CloudRuntimeException("Exception while retrieving guest type for network " + networkId, e);
                                    }

                                } catch (SQLException e) {
                                    throw new CloudRuntimeException("Exception while retrieving guest type for network " + networkId, e);
                                }
                            } // if
                        } // if
                    }
                }
            } // while
        } catch (SQLException e) {
            throw new CloudRuntimeException("Exception while Moving private zone information to dedicated resources", e);
        }
        s_logger.debug("Done updating vm nic secondary ip  account and domain ids");
    }

};