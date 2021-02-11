//,temp,Upgrade452to460.java,67,103,temp,Upgrade218to22.java,2177,2209
//,3
public class xxx {
    public void updateVMInstanceUserId(final Connection conn) {
        // For schemas before this, copy first user from an account_id which
        // deployed already running VMs
        s_logger.debug("Updating vm_instance column user_id using first user in vm_instance's account_id");
        final String vmInstanceSql = "SELECT id, account_id FROM `cloud`.`vm_instance`";
        final String userSql = "SELECT id FROM `cloud`.`user` where account_id=?";
        final String userIdUpdateSql = "update `cloud`.`vm_instance` set user_id=? where id=?";
        try (PreparedStatement selectStatement = conn.prepareStatement(vmInstanceSql)) {
            final ResultSet results = selectStatement.executeQuery();
            while (results.next()) {
                final long vmId = results.getLong(1);
                final long accountId = results.getLong(2);
                try (PreparedStatement selectUserStatement = conn.prepareStatement(userSql)) {
                    selectUserStatement.setLong(1, accountId);
                    final ResultSet userResults = selectUserStatement.executeQuery();
                    if (userResults.next()) {
                        final long userId = userResults.getLong(1);
                        try (PreparedStatement updateStatement = conn.prepareStatement(userIdUpdateSql)) {
                            updateStatement.setLong(1, userId);
                            updateStatement.setLong(2, vmId);
                            updateStatement.executeUpdate();
                        } catch (final SQLException e) {
                            throw new CloudRuntimeException("Unable to update user ID " + userId + " on vm_instance id=" + vmId, e);
                        }
                    }

                } catch (final SQLException e) {
                    throw new CloudRuntimeException("Unable to update user ID using accountId " + accountId + " on vm_instance id=" + vmId, e);
                }
            }
        } catch (final SQLException e) {
            throw new CloudRuntimeException("Unable to update user Ids for previously deployed VMs", e);
        }
        s_logger.debug("Done updating user Ids for previously deployed VMs");
        addRedundancyForNwAndVpc(conn);
        removeBumPriorityColumn(conn);
    }

};