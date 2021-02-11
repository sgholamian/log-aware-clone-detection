//,temp,Upgrade305to306.java,116,132,temp,Upgrade305to306.java,77,93
//,2
public class xxx {
    private void addIndexForAlert(Connection conn) {

        //First drop if it exists. (Due to patches shipped to customers some will have the index and some wont.)
        List<String> indexList = new ArrayList<String>();
        s_logger.debug("Dropping index i_alert__last_sent if it exists");
        indexList.add("i_alert__last_sent");
        DbUpgradeUtils.dropKeysIfExist(conn, "alert", indexList, false);

        //Now add index.
        try (PreparedStatement pstmt = conn.prepareStatement("ALTER TABLE `cloud`.`alert` ADD INDEX `i_alert__last_sent`(`last_sent`)");) {
            pstmt.executeUpdate();
            s_logger.debug("Added index i_alert__last_sent for table alert");
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to add index i_alert__last_sent to alert table for the column last_sent", e);
        }

    }

};