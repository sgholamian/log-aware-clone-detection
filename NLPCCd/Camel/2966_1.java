//,temp,sample_3290.java,2,10,temp,sample_4301.java,2,10
//,3
public class xxx {
public ChannelPipeline getPipeline() throws Exception {
ChannelPipeline pipeline = Channels.pipeline();
SslHandler sslHandler = configureServerSSLOnDemand();
if (sslHandler != null) {


log.info("server ssl handler configured and added as an interceptor against the channelpipeline");
}
}

};