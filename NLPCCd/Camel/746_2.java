//,temp,sample_912.java,2,14,temp,sample_923.java,2,16
//,3
public class xxx {
public void onErrorHandlerRemove(RouteContext routeContext, Processor errorHandler, ErrorHandlerFactory errorHandlerBuilder) {
if (!initialized) {
return;
}
Object me = getManagementObjectStrategy().getManagedObjectForErrorHandler(camelContext, routeContext, errorHandler, errorHandlerBuilder);
if (me != null) {
try {
unmanageObject(me);
} catch (Exception e) {


log.info("could not unregister error handler as errorhandler mbean");
}
}
}

};