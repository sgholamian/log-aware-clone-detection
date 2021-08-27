//,temp,sample_734.java,2,18,temp,sample_733.java,2,18
//,3
public class xxx {
public void dummy_method(){
Expression exp = routeContext.getCamelContext().resolveLanguage("simple").createExpression(message);
Logger logger = this.getLogger();
if (logger == null && ObjectHelper.isNotEmpty(loggerRef)) {
logger = CamelContextHelper.mandatoryLookup(routeContext.getCamelContext(), loggerRef, Logger.class);
}
if (logger == null) {
Map<String, Logger> availableLoggers = routeContext.lookupByType(Logger.class);
if (availableLoggers.size() == 1) {
logger = availableLoggers.values().iterator().next();
} else if (availableLoggers.size() > 1) {


log.info("more than one instance found in the registry falling back to create logger by name");
}
}
}

};