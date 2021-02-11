//,temp,Upgrade410to420.java,1365,1396,temp,Upgrade218to22.java,2177,2209
//,3
public class xxx {
    private void fix22xKVMSnapshots(Connection conn) {
        s_logger.debug("Updating KVM snapshots");
        try (PreparedStatement pstmt = conn.prepareStatement("select id, backup_snap_id from `cloud`.`snapshots` where hypervisor_type='KVM' and removed is null and backup_snap_id is not null");)
        {
            try(ResultSet rs = pstmt.executeQuery();) {
                while (rs.next()) {
                    long id = rs.getLong(1);
                    String backUpPath = rs.getString(2);
                    // Update Backup Path. Remove anything before /snapshots/
                    // e.g 22x Path /mnt/0f14da63-7033-3ca5-bdbe-fa62f4e2f38a/snapshots/1/2/6/i-2-6-VM_ROOT-6_20121219072022
                    // Above path should change to /snapshots/1/2/6/i-2-6-VM_ROOT-6_20121219072022
                    int index = backUpPath.indexOf("snapshots" + File.separator);
                    if (index > 1) {
                        String correctedPath = backUpPath.substring(index);
                        s_logger.debug("Updating Snapshot with id: " + id + " original backup path: " + backUpPath + " updated backup path: " + correctedPath);
                        try(PreparedStatement update_pstmt = conn.prepareStatement("UPDATE `cloud`.`snapshots` set backup_snap_id=? where id = ?");) {
                            update_pstmt.setString(1, correctedPath);
                            update_pstmt.setLong(2, id);
                            update_pstmt.executeUpdate();
                        }catch (SQLException e) {
                            throw new CloudRuntimeException("Unable to update backup id for KVM snapshots", e);
                        }
                    }
                }
                s_logger.debug("Done updating KVM snapshots");
            }catch (SQLException e) {
                throw new CloudRuntimeException("Unable to update backup id for KVM snapshots", e);
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to update backup id for KVM snapshots", e);
        }
    }

};