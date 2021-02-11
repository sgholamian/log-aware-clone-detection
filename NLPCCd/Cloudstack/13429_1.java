//,temp,sample_4973.java,2,13,temp,sample_193.java,2,12
//,3
public class xxx {
private void addIndexForAlert(Connection conn) {
List<String> indexList = new ArrayList<String>();
indexList.add("last_sent");
indexList.add("i_alert__last_sent");
DbUpgradeUtils.dropKeysIfExist(conn, "alert", indexList, false);
try(PreparedStatement pstmt = conn.prepareStatement("ALTER TABLE `cloud`.`alert` ADD INDEX `i_alert__last_sent`(`last_sent`)");) {
pstmt.executeUpdate();


log.info("added index i alert last sent for table alert");
}
}

};