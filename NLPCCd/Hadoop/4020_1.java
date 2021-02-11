//,temp,sample_1311.java,2,11,temp,sample_3762.java,2,11
//,3
public class xxx {
private void partUpload(boolean closingUpload) throws IOException {
if (backupStream != null) {
backupStream.close();
}
if (closingUpload && partUpload && backupFile.length() == 0) {


log.info("skipping upload of byte final partition");
}
}

};