//,temp,Upgrade410to420.java,2246,2260,temp,Upgrade410to420.java,445,460
//,3
public class xxx {
    private void addIndexForAlert(Connection conn) {
        //First drop if it exists. (Due to patches shipped to customers some will have the index and some wont.)
        List<String> indexList = new ArrayList<String>();
        s_logger.debug("Dropping index i_alert__last_sent if it exists");
        indexList.add("last_sent"); // in 4.1, we created this index that is not in convention.
        indexList.add("i_alert__last_sent");
        DbUpgradeUtils.dropKeysIfExist(conn, "alert", indexList, false);
        //Now add index.
        try(PreparedStatement pstmt = conn.prepareStatement("ALTER TABLE `cloud`.`alert` ADD INDEX `i_alert__last_sent`(`last_sent`)");)
        {
            pstmt.executeUpdate();
            s_logger.debug("Added index i_alert__last_sent for table alert");
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to add index i_alert__last_sent to alert table for the column last_sent", e);
        }
    }

};