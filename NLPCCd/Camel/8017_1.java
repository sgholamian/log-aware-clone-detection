//,temp,sample_2783.java,2,16,temp,sample_5046.java,2,17
//,3
public class xxx {
protected void setupVmProtocol(String uri) {
boolean minaLogger = configuration.isMinaLogger();
List<IoFilter> filters = configuration.getFilters();
address = new VmPipeAddress(configuration.getPort());
connector = new VmPipeConnector();
if (minaLogger) {
connector.getFilterChain().addLast("logger", new LoggingFilter());
}
appendIoFiltersToChain(filters, connector.getFilterChain());
if (configuration.getSslContextParameters() != null) {


log.info("using vm protocol but an sslcontextparameters instance was provided sslcontextparameters is only supported on the tcp protocol");
}
}

};