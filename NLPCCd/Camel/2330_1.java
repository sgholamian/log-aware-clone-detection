//,temp,sample_3119.java,2,10,temp,sample_2181.java,2,10
//,2
public class xxx {
protected void initChannel(Channel ch) throws Exception {
ChannelPipeline channelPipeline = ch.pipeline();
SslHandler sslHandler = configureClientSSLOnDemand();
if (sslHandler != null) {


log.info("client ssl handler configured and added to the channelpipeline");
}
}

};