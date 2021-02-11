//,temp,Upgrade410to420.java,952,1010,temp,Upgrade218to22.java,1163,1245
//,3
public class xxx {
    private void updateUserStats(Connection conn) {
        try (
            // update device_type information
                PreparedStatement pstmt = conn.prepareStatement("UPDATE user_statistics SET device_type='DomainRouter'");
            ){
            pstmt.executeUpdate();
            s_logger.debug("Upgraded userStatistcis with device_type=DomainRouter");

            // update device_id infrormation
            try (
                    PreparedStatement selectUserStatistics = conn.prepareStatement("SELECT id, account_id, data_center_id FROM user_statistics");
                    ResultSet rs = selectUserStatistics.executeQuery();
                ) {
                while (rs.next()) {
                    Long id = rs.getLong(1); // user stats id
                    Long accountId = rs.getLong(2); // account id
                    Long dataCenterId = rs.getLong(3); // zone id

                    try (PreparedStatement selectNetworkType = conn.prepareStatement("SELECT networktype from data_center where id=?");) {
                        selectNetworkType.setLong(1, dataCenterId);
                        try (ResultSet dcSet = selectNetworkType.executeQuery();) {
                            if (!dcSet.next()) {
                                s_logger.error("Unable to get data_center information as a part of user_statistics update");
                                throw new CloudRuntimeException("Unable to get data_center information as a part of user_statistics update");
                            }
                            String dataCenterType = dcSet.getString(1);
                            if (dataCenterType.equalsIgnoreCase("basic")) {
                                accountId = 1L;
                            }
                        }
                    }
                    try (PreparedStatement selectDomainRouterIds = conn.prepareStatement("SELECT id from vm_instance where account_id=? AND data_center_id=? AND type='DomainRouter'");) {
                        selectDomainRouterIds.setLong(1, accountId);
                        selectDomainRouterIds.setLong(2, dataCenterId);
                        try (ResultSet domainRouterIdResult = selectDomainRouterIds.executeQuery();) {
                            Long deviceId = 0L;
                            if (!domainRouterIdResult.next()) {
                                // check if there are any non-removed user vms existing for this account
                                // if all vms are expunged, and there is no domR, just skip this record
                                try (PreparedStatement selectnonRemovedVms = conn.prepareStatement("SELECT * from vm_instance where account_id=? AND data_center_id=? AND removed IS NULL");) {
                                    selectnonRemovedVms.setLong(1, accountId);
                                    selectnonRemovedVms.setLong(2, dataCenterId);
                                    try (ResultSet nonRemovedVms = selectnonRemovedVms.executeQuery();) {
                                        if (nonRemovedVms.next()) {
                                            s_logger.warn("Failed to find domR for for account id=" + accountId + " in zone id=" + dataCenterId +
                                                    "; will try to locate domR based on user_vm info");
                                            //try to get domR information from the user_vm belonging to the account
                                            try (PreparedStatement selectNetworkType =
                                                    conn.prepareStatement("SELECT u.domain_router_id from user_vm u, vm_instance v where u.account_id=? AND v.data_center_id=? AND v.removed IS NULL AND u.domain_router_id is NOT NULL");) {
                                                selectNetworkType.setLong(1, accountId);
                                                selectNetworkType.setLong(2, dataCenterId);
                                                try (ResultSet userVmSet = selectNetworkType.executeQuery();) {
                                                    if (!userVmSet.next()) {
                                                        s_logger.warn("Skipping user_statistics upgrade for account id=" + accountId + " in datacenter id=" + dataCenterId);
                                                        continue;
                                                    }
                                                    deviceId = userVmSet.getLong(1);
                                                }
                                            }
                                        } else {
                                            s_logger.debug("Account id=" + accountId + " doesn't own any user vms and domRs, so skipping user_statistics update");
                                            continue;
                                        }
                                    }
                                }
                            } else {
                                deviceId = domainRouterIdResult.getLong(1);
                            }
                            try (PreparedStatement updateUserStatistics = conn.prepareStatement("UPDATE user_statistics SET device_id=? where id=?");) {
                                updateUserStatistics.setLong(1, deviceId);
                                updateUserStatistics.setLong(2, id);
                                updateUserStatistics.executeUpdate();
                            }
                        }
                    }
                }
            }
            s_logger.debug("Upgraded userStatistcis with deviceId(s)");

        } catch (Exception e) {
            throw new CloudRuntimeException("Failed to migrate usage events: ", e);
        }
    }

};