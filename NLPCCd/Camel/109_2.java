//,temp,sample_3414.java,2,16,temp,sample_3888.java,4,17
//,3
public class xxx {
private boolean doProceed(AsyncCallback callback) throws Exception {
if (dynamicRouter != null) {
if (!dynamicRouter.isStarted()) {
ServiceHelper.startService(dynamicRouter);
}
Expression expression = new DynamicRouterExpression(pojo);
return dynamicRouter.doRoutingSlip(exchange, expression, callback);
}
if (LOG.isTraceEnabled()) {


log.info("invoking on bean with arguments for exchange");
}
}

};