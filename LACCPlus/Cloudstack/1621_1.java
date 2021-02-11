//,temp,Upgrade301to302.java,182,201,temp,Upgrade222to224Premium.java,50,73
//,3
public class xxx {
    private void changeEngine(Connection conn) {
        s_logger.debug("Fixing engine and row_format for op_lock and op_nwgrp_work tables");
        String sqlOpLock = "ALTER TABLE `cloud`.`op_lock` ENGINE=MEMORY, ROW_FORMAT = FIXED";
        try (
                PreparedStatement pstmt = conn.prepareStatement(sqlOpLock);
            ) {
            pstmt.executeUpdate();
        } catch (Exception e) {
            s_logger.debug("Failed do execute the statement " + sqlOpLock + ", moving on as it's not critical fix");
        }

        String sqlOpNwgrpWork = "ALTER TABLE `cloud`.`op_nwgrp_work` ENGINE=MEMORY, ROW_FORMAT = FIXED";
        try  (
                PreparedStatement pstmt = conn.prepareStatement(sqlOpNwgrpWork);
             ) {
            pstmt.executeUpdate();
        } catch (Exception e) {
            s_logger.debug("Failed do execute the statement " + sqlOpNwgrpWork + ", moving on as it's not critical fix");
        }
    }

};