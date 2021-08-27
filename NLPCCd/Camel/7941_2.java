//,temp,sample_3796.java,2,17,temp,sample_3735.java,2,17
//,3
public class xxx {
public void dummy_method(){
channelFactory.init(configuration.getPort());
ChannelPipelineFactory pipelineFactory = new HttpServerSharedPipelineFactory(configuration, channelFactory, classResolver);
String port = Matcher.quoteReplacement("" + configuration.getPort());
String pattern = threadPattern;
pattern = pattern.replaceFirst("#port#", port);
ThreadFactory tf = new CamelThreadFactory(pattern, "NettySharedHttpServer", true);
bootstrapFactory = new HttpServerBootstrapFactory(channelFactory, false);
bootstrapFactory.init(tf, configuration, pipelineFactory);
ServiceHelper.startServices(channelFactory);
if (startServer) {


log.info("starting nettysharedhttpserver on");
}
}

};