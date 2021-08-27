//,temp,sample_6495.java,2,12,temp,sample_6496.java,2,15
//,3
public class xxx {
private void createWriter(ProducerType producerType) {
switch (producerType) {
case TCP: {
dataWriter = new TcpDataWriter(endpoint, buildSplunkArgs());
((TcpDataWriter)dataWriter).setPort(endpoint.getConfiguration().getTcpReceiverPort());
break;
}
case SUBMIT: {


log.info("creating submitdatawriter");
}
}
}

};