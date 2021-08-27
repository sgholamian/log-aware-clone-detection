//,temp,sample_2757.java,2,10,temp,sample_2096.java,2,11
//,3
public class xxx {
public ChannelPipeline getPipeline() throws Exception {
ChannelPipeline pipeline = Channels.pipeline();
SslHandler sslHandler = configureServerSSLOnDemand();
if (sslHandler != null) {
sslHandler.setCloseOnSSLException(true);


log.info("server ssl handler configured and added as an interceptor against the channelpipeline");
}
}

};