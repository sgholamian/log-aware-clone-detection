//,temp,sample_2214.java,2,13,temp,sample_8308.java,2,14
//,3
public class xxx {
private void writeSlowFile() throws Exception {
createDirectory(FTP_ROOT_DIR + "/changed");
FileOutputStream fos = new FileOutputStream(FTP_ROOT_DIR + "/changed/slowfile.dat", true);
for (int i = 0; i < 20; i++) {
fos.write(("Line " + i + LS).getBytes());
Thread.sleep(200);
}
fos.flush();
fos.close();


log.info("writing slow file done");
}

};