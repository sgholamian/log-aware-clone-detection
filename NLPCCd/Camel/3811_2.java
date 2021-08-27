//,temp,sample_734.java,2,18,temp,sample_733.java,2,18
//,3
public class xxx {
public void dummy_method(){
ObjectHelper.notEmpty(message, "message", this);
Expression exp = routeContext.getCamelContext().resolveLanguage("simple").createExpression(message);
Logger logger = this.getLogger();
if (logger == null && ObjectHelper.isNotEmpty(loggerRef)) {
logger = CamelContextHelper.mandatoryLookup(routeContext.getCamelContext(), loggerRef, Logger.class);
}
if (logger == null) {
Map<String, Logger> availableLoggers = routeContext.lookupByType(Logger.class);
if (availableLoggers.size() == 1) {
logger = availableLoggers.values().iterator().next();


log.info("using custom logger");
}
}
}

};