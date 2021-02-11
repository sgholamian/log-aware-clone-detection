//,temp,Upgrade222to224.java,383,432,temp,Upgrade222to224.java,174,230
//,3
public class xxx {
    private void updateClusterIdInOpHostCapacity(Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PreparedStatement pstmtUpdate = null;
        try {
            // Host and Primary storage capacity types
            pstmt = conn.prepareStatement("SELECT host_id, capacity_type FROM op_host_capacity WHERE capacity_type IN (0,1,2,3)");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                long hostId = rs.getLong(1);
                short capacityType = rs.getShort(2);
                String updateSQLPrefix = "Update op_host_capacity set cluster_id = (select cluster_id from ";
                String updateSQLSuffix = " where id = ? ) where host_id = ?";
                String tableName = "host";
                switch (capacityType) {
                    case Capacity.CAPACITY_TYPE_MEMORY:
                    case Capacity.CAPACITY_TYPE_CPU:
                        tableName = "host";
                        break;
                    case Capacity.CAPACITY_TYPE_STORAGE:
                    case Capacity.CAPACITY_TYPE_STORAGE_ALLOCATED:
                        tableName = "storage_pool";
                        break;
                }
                pstmtUpdate = conn.prepareStatement(updateSQLPrefix + tableName + updateSQLSuffix);
                pstmtUpdate.setLong(1, hostId);
                pstmtUpdate.setLong(2, hostId);
                pstmtUpdate.executeUpdate();
                pstmtUpdate.close();
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to update the cluster Ids in Op_Host_capacity table", e);
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