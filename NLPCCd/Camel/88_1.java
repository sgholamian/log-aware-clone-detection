//,temp,sample_1951.java,2,10,temp,sample_5177.java,2,11
//,3
public class xxx {
protected void initChannel(Channel ch) throws Exception {
ChannelPipeline channelPipeline = ch.pipeline();
SslHandler sslHandler = configureServerSSLOnDemand();
if (sslHandler != null) {


log.info("server ssl handler configured and added as an interceptor against the channelpipeline");
}
}

};