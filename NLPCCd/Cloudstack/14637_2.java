//,temp,sample_9344.java,2,18,temp,sample_8283.java,2,18
//,2
public class xxx {
public void dummy_method(){
DocumentBuilder builder = factory.newDocumentBuilder();
Document doc = builder.parse(is);
returnBody = doc.getDocumentElement();
doc.getDocumentElement().normalize();
Element jobStatusTag = (Element)returnBody.getElementsByTagName("jobstatus").item(0);
String jobStatus = jobStatusTag.getTextContent();
if (jobStatus.equals("0")) {
try {
Thread.sleep(1000);
} catch (InterruptedException e) {


log.info("ignored interupted while during async job result query");
}
}
}

};