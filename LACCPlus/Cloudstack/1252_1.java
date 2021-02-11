//,temp,Upgrade2214to30.java,1012,1063,temp,Upgrade2214to30.java,382,419
//,3
public class xxx {
    private void migrateUserConcentratedPlannerChoice(Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("SELECT value FROM `cloud`.`configuration` where name = 'use.user.concentrated.pod.allocation'");
            rs = pstmt.executeQuery();
            Boolean isuserconcentrated = false;
            if (rs.next()) {
                String value = rs.getString(1);
                isuserconcentrated = new Boolean(value);
            }
            rs.close();
            pstmt.close();

            if (isuserconcentrated) {
                String currentAllocationAlgo = "random";
                pstmt = conn.prepareStatement("SELECT value FROM `cloud`.`configuration` where name = 'vm.allocation.algorithm'");
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    currentAllocationAlgo = rs.getString(1);
                }
                rs.close();
                pstmt.close();

                String newAllocAlgo = "userconcentratedpod_random";
                if ("random".equalsIgnoreCase(currentAllocationAlgo)) {
                    newAllocAlgo = "userconcentratedpod_random";
                } else {
                    newAllocAlgo = "userconcentratedpod_firstfit";
                }

                pstmt = conn.prepareStatement("UPDATE `cloud`.`configuration` SET value = ? WHERE name = 'vm.allocation.algorithm'");
                pstmt.setString(1, newAllocAlgo);
                pstmt.executeUpdate();

            }

        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to migrate the user_concentrated planner choice", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                s_logger.info("[ignored]",e);
            }
        }
    }

};