//,temp,sample_23.java,2,9,temp,sample_3989.java,2,9
//,3
public class xxx {
public void init(CamelContext camelContext, NettyServerBootstrapConfiguration configuration, ChannelInitializer<Channel> pipelineFactory) {
super.init(camelContext, configuration, pipelineFactory);
this.port = configuration.getPort();
this.bootstrapConfiguration = configuration;


log.info("bootstrapfactory on port is using bootstrap configuration");
}

};