//,temp,sample_5999.java,2,17,temp,sample_5998.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (isTracing()) {
}
if (isUseMDCLogging()) {
}
if (isHandleFault()) {
if (HandleFault.getHandleFault(this) == null) {
addInterceptStrategy(new HandleFault());
}
}
if (getDelayer() != null && getDelayer() > 0) {


log.info("delayer is enabled with ms on camelcontext");
}
}

};