//,temp,sample_739.java,2,14,temp,sample_5120.java,2,13
//,3
public class xxx {
private void writeSlowFile() throws Exception {
createDirectory(FTP_ROOT_DIR + "/");
FileOutputStream fos = new FileOutputStream(FTP_ROOT_DIR + "/slowfile.dat", true);
for (int i = 0; i < 20; i++) {
fos.write(("Line " + i + LS).getBytes());
Thread.sleep(200);
}
fos.flush();
fos.close();


log.info("writing slow file done");
}

};