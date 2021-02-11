//,temp,Upgrade222to224.java,383,432,temp,Upgrade222to224.java,174,230
//,3
public class xxx {
    private void updateTotalCPUInOpHostCapacity(Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PreparedStatement pstmtUpdate = null;
        try {
            // Load all Routing hosts
            s_logger.debug("Updating total CPU capacity entries in op_host_capacity");
            pstmt = conn.prepareStatement("SELECT id, cpus, speed FROM host WHERE type = 'Routing'");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                long hostId = rs.getLong(1);
                int cpus = rs.getInt(2);
                long speed = rs.getLong(3);

                long totalCapacity = cpus * speed;

                String updateSQL = "UPDATE op_host_capacity SET total_capacity = ? WHERE host_id = ? AND capacity_type = 1";
                pstmtUpdate = conn.prepareStatement(updateSQL);
                pstmtUpdate.setLong(1, totalCapacity);
                pstmtUpdate.setLong(2, hostId);
                pstmtUpdate.executeUpdate();
                pstmtUpdate.close();
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to update the total host CPU capacity in Op_Host_capacity table", e);
        } finally {
            if (pstmtUpdate != null) {
                try {
                    pstmtUpdate.close();
                } catch (SQLException e) {
                    s_logger.info("[ignored]",e);
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    s_logger.info("[ignored]",e);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    s_logger.info("[ignored]",e);
                }
            }

        }
    }

};