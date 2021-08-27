//,temp,sample_4138.java,2,12,temp,sample_1852.java,2,15
//,3
public class xxx {
private void writeFiles() throws Exception {
FileOutputStream fos = new FileOutputStream("target/marker/in/file1.dat");
FileOutputStream fos2 = new FileOutputStream("target/marker/in/file2.dat");
for (int i = 0; i < 20; i++) {
fos.write(("Line " + i + LS).getBytes());
fos2.write(("Line " + i + LS).getBytes());


log.info("writing line");
}
}

};