//,temp,sample_3119.java,2,10,temp,sample_2181.java,2,10
//,2
public class xxx {
protected void initChannel(Channel ch) throws Exception {
ChannelPipeline pipeline = ch.pipeline();
SslHandler sslHandler = configureServerSSLOnDemand();
if (sslHandler != null) {


log.info("server ssl handler configured and added as an interceptor against the channelpipeline");
}
}

};