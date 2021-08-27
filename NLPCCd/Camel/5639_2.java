//,temp,sample_4138.java,2,12,temp,sample_1852.java,2,15
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
File file = new File("target/exclusiveread/slowfile/hello.txt");
FileOutputStream fos = new FileOutputStream(file);
fos.write("Hello World".getBytes());
for (int i = 0; i < 3; i++) {
Thread.sleep(100);
fos.write(("Line #" + i).getBytes());
}
fos.write("Bye World".getBytes());
fos.close();


log.info("done creating slowfile");
}

};