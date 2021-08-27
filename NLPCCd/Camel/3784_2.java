//,temp,sample_4302.java,2,18,temp,sample_3120.java,2,18
//,3
public class xxx {
public void dummy_method(){
List<ChannelHandler> encoders = producer.getConfiguration().getEncoders();
for (int x = 0; x < encoders.size(); x++) {
ChannelHandler encoder = encoders.get(x);
if (encoder instanceof ChannelHandlerFactory) {
encoder = ((ChannelHandlerFactory) encoder).newChannelHandler();
}
addToPipeline("encoder-" + x, channelPipeline, encoder);
}
if (producer.getConfiguration().getRequestTimeout() > 0) {
if (LOG.isTraceEnabled()) {


log.info("using request timeout millis");
}
}
}

};