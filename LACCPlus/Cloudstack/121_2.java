//,temp,Upgrade410to420.java,1294,1316,temp,Upgrade410to420.java,520,545
//,3
public class xxx {
    private void setKVMSnapshotFlag(Connection conn) {
        s_logger.debug("Verify and set the KVM snapshot flag if snapshot was used. ");
        try(PreparedStatement pstmt = conn.prepareStatement("select count(*) from `cloud`.`snapshots` where hypervisor_type = 'KVM'");)
        {
            int numRows = 0;
            try(ResultSet rs = pstmt.executeQuery();) {
                if (rs.next()) {
                    numRows = rs.getInt(1);
                }
                if (numRows > 0) {
                    //Add the configuration flag
                    try(PreparedStatement update_pstmt = conn.prepareStatement("UPDATE `cloud`.`configuration` SET value = ? WHERE name = 'kvm.snapshot.enabled'");) {
                        update_pstmt.setString(1, "true");
                        update_pstmt.executeUpdate();
                    }catch (SQLException e) {
                        throw new CloudRuntimeException("Failed to read the snapshot table for KVM upgrade. ", e);
                    }
                }
            }catch (SQLException e) {
                throw new CloudRuntimeException("Failed to read the snapshot table for KVM upgrade. ", e);
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Failed to read the snapshot table for KVM upgrade. ", e);
        }
        s_logger.debug("Done set KVM snapshot flag. ");
    }

};