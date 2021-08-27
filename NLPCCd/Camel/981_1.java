//,temp,sample_8100.java,2,16,temp,sample_1396.java,2,16
//,3
public class xxx {
public void dummy_method(){
serverBootstrap.setOption("connectTimeoutMillis", configuration.getConnectTimeout());
if (configuration.getBacklog() > 0) {
serverBootstrap.setOption("backlog", configuration.getBacklog());
}
if (configuration.getOptions() != null) {
for (Map.Entry<String, Object> entry : configuration.getOptions().entrySet()) {
serverBootstrap.setOption(entry.getKey(), entry.getValue());
}
}
serverBootstrap.setPipelineFactory(pipelineFactory);


log.info("serverbootstrap connecting to");
}

};