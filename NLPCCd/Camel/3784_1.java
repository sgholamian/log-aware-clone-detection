//,temp,sample_4302.java,2,18,temp,sample_3120.java,2,18
//,3
public class xxx {
public void dummy_method(){
for (int x = 0; x < decoders.size(); x++) {
ChannelHandler decoder = decoders.get(x);
if (decoder instanceof ChannelHandlerFactory) {
decoder = ((ChannelHandlerFactory) decoder).newChannelHandler();
}
pipeline.addLast("decoder-" + x, decoder);
}
pipeline.addLast("aggregator", new HttpObjectAggregator(configuration.getChunkedMaxContentLength()));
if (producer.getConfiguration().getRequestTimeout() > 0) {
if (LOG.isTraceEnabled()) {


log.info("using request timeout millis");
}
}
}

};