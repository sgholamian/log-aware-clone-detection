//,temp,sample_4603.java,2,12,temp,sample_4604.java,2,14
//,3
public class xxx {
private void deleteTableData(Path tablePath, boolean ifPurge) {
if (tablePath != null) {
try {
wh.deleteDir(tablePath, true, ifPurge);
} catch (Exception e) {


log.info("failed to delete table directory");
}
}
}

};