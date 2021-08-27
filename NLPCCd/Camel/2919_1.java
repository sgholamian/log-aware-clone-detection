//,temp,sample_2479.java,2,11,temp,sample_2368.java,2,14
//,3
public class xxx {
public Consumer createConsumer(Processor processor) throws Exception {
NettyHttpConsumer answer = new NettyHttpConsumer(this, processor, getConfiguration());
configureConsumer(answer);
if (nettySharedHttpServer != null) {
answer.setNettyServerBootstrapFactory(nettySharedHttpServer.getServerBootstrapFactory());


log.info("nettyhttpconsumer is using nettysharedhttpserver on port");
}
}

};