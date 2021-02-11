//,temp,Upgrade218to22.java,2211,2259,temp,Upgrade218to22.java,2177,2209
//,3
public class xxx {
    private void cleanupVolumes(Connection conn) {
        try (
                PreparedStatement selectVolumes = conn.prepareStatement("SELECT id, instance_id, account_id from volumes where destroyed=127");
                ResultSet selectedVolumes = selectVolumes.executeQuery();
            ){
            while (selectedVolumes.next()) {
                Long id = selectedVolumes.getLong(1);
                s_logger.debug("Volume id is " + id);
                Long instanceId = selectedVolumes.getLong(2);
                Long accountId = selectedVolumes.getLong(3);

                boolean removeVolume = false;

                try (PreparedStatement selectAccounts = conn.prepareStatement("SELECT * from account where id=? and removed is not null");) {
                    selectAccounts.setLong(1, accountId);
                    try(ResultSet selectedAccounts = selectAccounts.executeQuery();) {

                        if (selectedAccounts.next()) {
                            removeVolume = true;
                        }

                        if (instanceId != null) {
                            try(PreparedStatement selectInstances = conn.prepareStatement("SELECT * from vm_instance where id=? and removed is not null");) {
                                selectInstances.setLong(1, instanceId);
                                try (ResultSet selectedInstances = selectInstances.executeQuery();) {

                                    if (selectedInstances.next()) {
                                        removeVolume = true;
                                    }
                                }
                            }
                        }

                        if (removeVolume) {
                            try(PreparedStatement pstmt = conn.prepareStatement("UPDATE volumes SET state='Destroy' WHERE id=?");) {
                                pstmt.setLong(1, id);
                                pstmt.executeUpdate();
                                s_logger.debug("Volume with id=" + id + " is marked with Destroy state as a part of volume cleanup (it's Destroyed had 127 value)");
                            }
                        }
                    }
                }
            }
            s_logger.debug("Finished cleaning up volumes with incorrect Destroyed field (127)");
        } catch (Exception e) {
            s_logger.error("Failed to cleanup volumes with incorrect Destroyed field (127):", e);
            throw new CloudRuntimeException("Failed to cleanup volumes with incorrect Destroyed field (127):", e);
        }
    }

};