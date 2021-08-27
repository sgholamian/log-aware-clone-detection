//,temp,sample_6820.java,2,16,temp,sample_6709.java,2,17
//,3
public class xxx {
public void dummy_method(){
Exchange exchange = this.getEndpoint().createExchange();
Message message = new DefaultMessage(this.getEndpoint().getCamelContext());
String fileName = StringUtils.substringAfterLast(status.getPath().toString(), "/");
message.setHeader(Exchange.FILE_NAME, fileName);
message.setHeader(Exchange.FILE_PATH, status.getPath().toString());
if (key.value != null) {
message.setHeader(HdfsHeader.KEY.name(), key.value);
}
message.setBody(value.value);
exchange.setIn(message);


log.info("processing file");
}

};