//,temp,sample_8307.java,2,11,temp,sample_2213.java,2,10
//,3
public class xxx {
private void writeSlowFile() throws Exception {
createDirectory(FTP_ROOT_DIR + "/changed");
FileOutputStream fos = new FileOutputStream(FTP_ROOT_DIR + "/changed/slowfile.dat", true);
for (int i = 0; i < 20; i++) {
fos.write(("Line " + i + LS).getBytes());


log.info("writing line");
}
}

};