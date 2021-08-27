//,temp,sample_1761.java,2,18,temp,sample_4216.java,2,18
//,2
public class xxx {
public void dummy_method(){
mock.expectedBodiesReceived(expected);
ProducerTemplate triggerTemplate = context.createProducerTemplate();
triggerTemplate.sendBody("vm:trigger", "");
assertMockEndpointsSatisfied();
long startFileDeletionCheckTime = System.currentTimeMillis();
boolean fileExists = true;
while (System.currentTimeMillis() - startFileDeletionCheckTime < 3000) {
File file = new File(FTP_ROOT_DIR + "/hello.txt");
fileExists = file.exists();
if (fileExists) {


log.info("will check that file has been deleted again in");
}
}
}

};