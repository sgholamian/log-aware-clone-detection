//,temp,sample_2214.java,2,13,temp,sample_8308.java,2,14
//,3
public class xxx {
private void writeSlowFile() throws Exception {
FileOutputStream fos = new FileOutputStream("target/changed/in/slowfile.dat");
for (int i = 0; i < 20; i++) {
fos.write(("Line " + i + LS).getBytes());
Thread.sleep(50);
}
fos.flush();
fos.close();


log.info("writing slow file done");
}

};