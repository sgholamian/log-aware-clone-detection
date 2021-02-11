//,temp,sample_7954.java,2,14,temp,sample_8459.java,2,14
//,2
public class xxx {
private void updateRegionEntries(Connection conn) {
final Properties dbProps = DbProperties.getDbProperties();
int region_id = 1;
String regionId = dbProps.getProperty("region.id");
if (regionId != null) {
region_id = Integer.parseInt(regionId);
}
try (PreparedStatement pstmt = conn.prepareStatement("update `cloud`.`region` set id = ?");) {


log.info("updating region table with id");
}
}

};