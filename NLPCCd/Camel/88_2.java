//,temp,sample_1951.java,2,10,temp,sample_5177.java,2,11
//,3
public class xxx {
public ChannelPipeline getPipeline() throws Exception {
ChannelPipeline channelPipeline = Channels.pipeline();
SslHandler sslHandler = configureServerSSLOnDemand();
if (sslHandler != null) {
sslHandler.setCloseOnSSLException(true);


log.info("server ssl handler configured and added as an interceptor against the channelpipeline");
}
}

};