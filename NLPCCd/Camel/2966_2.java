//,temp,sample_3290.java,2,10,temp,sample_4301.java,2,10
//,3
public class xxx {
protected void initChannel(Channel ch) throws Exception {
ChannelPipeline pipeline = ch.pipeline();
SslHandler sslHandler = configureClientSSLOnDemand();
if (sslHandler != null) {


log.info("client ssl handler configured and added as an interceptor against the channelpipeline");
}
}

};