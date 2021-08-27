//,temp,sample_4181.java,2,14,temp,sample_1851.java,2,13
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
File file = new File(FTP_ROOT_DIR + "/slowfile/hello.txt");
FileOutputStream fos = new FileOutputStream(file);
FileLock lock = fos.getChannel().lock();
fos.write("Hello World".getBytes());
for (int i = 0; i < 3; i++) {
Thread.sleep(1000);
fos.write(("Line #" + i).getBytes());


log.info("appending to slowfile");
}
}

};