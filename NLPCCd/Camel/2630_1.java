//,temp,sample_3734.java,2,13,temp,sample_3795.java,2,13
//,2
public class xxx {
protected void doStart() throws Exception {
ObjectHelper.notNull(configuration, "setNettyServerBootstrapConfiguration() must be called with a NettyServerBootstrapConfiguration instance", this);
if (configuration.getPort() <= 0) {
throw new IllegalArgumentException("Port must be configured on NettySharedHttpServerBootstrapConfiguration " + configuration);
}
if (ObjectHelper.isEmpty(configuration.getHost())) {
throw new IllegalArgumentException("Host must be configured on NettySharedHttpServerBootstrapConfiguration " + configuration);
}


log.info("nettysharedhttpserver using configuration");
}

};