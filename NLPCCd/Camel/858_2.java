//,temp,sample_7262.java,2,15,temp,sample_7266.java,2,13
//,3
public class xxx {
public static boolean deleteFile(File file) {
if (!file.exists()) {
return false;
}
boolean deleted = false;
int count = 0;
while (!deleted && count < 3) {


log.info("retrying attempt to delete file");
}
}

};