//,temp,sample_7262.java,2,15,temp,sample_7266.java,2,13
//,3
public class xxx {
public static boolean renameFile(File from, File to, boolean copyAndDeleteOnRenameFail) throws IOException {
if (!from.exists()) {
return false;
}
boolean renamed = false;
int count = 0;
while (!renamed && count < 3) {
if (LOG.isDebugEnabled() && count > 0) {


log.info("retrying attempt to rename file from to");
}
}
}

};