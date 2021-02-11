//,temp,Upgrade218to22.java,2297,2326,temp,Upgrade218to22.java,2211,2259
//,3
public class xxx {
    private void cleanupLbVmMaps(Connection conn) {
        try (
                PreparedStatement pstmt = conn.prepareStatement("SELECT DISTINCT load_balancer_id FROM load_balancer_vm_map");
                ResultSet rs = pstmt.executeQuery();
            ){
            while (rs.next()) {
                long lbId = rs.getLong(1);
                try (PreparedStatement pstmt1 = conn.prepareStatement("SELECT * FROM load_balancer where id=?");) {
                    pstmt1.setLong(1, lbId);
                    try (ResultSet rs1 = pstmt1.executeQuery();) {

                        try (
                                PreparedStatement pstmt2 = conn.prepareStatement("SELECT * from event where type like '%lb.delete%' and parameters like '%id=" + lbId + "%'");
                                ResultSet rs2 = pstmt2.executeQuery();
                            ) {
                            if (!rs1.next() && rs2.next()) {
                                s_logger.debug("Removing load balancer vm mappings for lb id=" + lbId + " as a part of cleanup");
                                try (PreparedStatement delete = conn.prepareStatement("DELETE FROM load_balancer_vm_map where load_balancer_id=?");) {
                                    delete.setLong(1, lbId);
                                    delete.executeUpdate();
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Failed to cleanup orpahned lb-vm mappings due to:", e);
        }
    }

};