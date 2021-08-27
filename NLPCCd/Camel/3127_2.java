//,temp,sample_906.java,2,16,temp,sample_922.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (!shouldRegister(errorHandler, null)) {
return;
}
Object me = getManagementObjectStrategy().getManagedObjectForErrorHandler(camelContext, routeContext, errorHandler, errorHandlerBuilder);
if (getManagementStrategy().isManaged(me, null)) {
return;
}
try {
manageObject(me);
} catch (Exception e) {


log.info("could not register error handler builder as errorhandler mbean");
}
}

};