//,temp,sample_5924.java,2,17,temp,sample_5923.java,2,18
//,3
public class xxx {
public void dummy_method(){
boolean endpointInterceptor = false;
if (processor instanceof Channel) {
Channel channel = (Channel) processor;
Processor next = channel.getNextProcessor();
if (next instanceof InterceptEndpointProcessor) {
endpointInterceptor = true;
}
}
if (endpointInterceptor) {
} else {


log.info("adding event driven processor");
}
}

};