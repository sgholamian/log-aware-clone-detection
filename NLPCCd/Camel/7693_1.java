//,temp,sample_7373.java,2,14,temp,sample_7374.java,2,15
//,3
public class xxx {
protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
LoggingLevel level = getLoggingLevel(parameters);
Logger providedLogger = getLogger(parameters);
if (providedLogger == null) {
Map<String, Logger> availableLoggers = getCamelContext().getRegistry().findByTypeWithName(Logger.class);
if (availableLoggers.size() == 1) {
providedLogger = availableLoggers.values().iterator().next();


log.info("using custom logger");
}
}
}

};