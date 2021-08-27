//,temp,sample_924.java,2,12,temp,sample_921.java,2,12
//,3
public class xxx {
public void onErrorHandlerAdd(RouteContext routeContext, Processor errorHandler, ErrorHandlerFactory errorHandlerBuilder) {
if (!shouldRegister(errorHandler, null)) {
return;
}
Object me = getManagementObjectStrategy().getManagedObjectForErrorHandler(camelContext, routeContext, errorHandler, errorHandlerBuilder);
if (getManagementStrategy().isManaged(me, null)) {


log.info("the error handler builder is already managed");
}
}

};