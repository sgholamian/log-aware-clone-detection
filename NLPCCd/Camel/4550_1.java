//,temp,sample_786.java,2,11,temp,sample_5447.java,2,11
//,2
public class xxx {
public ChannelPipeline getPipeline() throws Exception {
ChannelPipeline pipeline = Channels.pipeline();
SslHandler sslHandler = configureClientSSLOnDemand();
if (sslHandler != null) {
sslHandler.setCloseOnSSLException(true);


log.info("client ssl handler configured and added as an interceptor against the channelpipeline");
}
}

};