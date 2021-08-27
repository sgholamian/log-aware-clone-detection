//,temp,sample_4181.java,2,14,temp,sample_1851.java,2,13
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
File file = new File("target/exclusiveread/slowfile/hello.txt");
FileOutputStream fos = new FileOutputStream(file);
fos.write("Hello World".getBytes());
for (int i = 0; i < 3; i++) {
Thread.sleep(100);
fos.write(("Line #" + i).getBytes());


log.info("appending to slowfile");
}
}

};