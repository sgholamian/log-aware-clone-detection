//,temp,sample_8307.java,2,11,temp,sample_2213.java,2,10
//,3
public class xxx {
private void writeSlowFile() throws Exception {
FileOutputStream fos = new FileOutputStream("target/changed/in/slowfile.dat");
for (int i = 0; i < 20; i++) {
fos.write(("Line " + i + LS).getBytes());


log.info("writing line");
}
}

};