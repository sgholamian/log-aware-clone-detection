//,temp,sample_649.java,2,13,temp,sample_2402.java,2,12
//,3
public class xxx {
public static CallContext unregister() {
CallContext context = s_currentContext.get();
if (context == null) {
return null;
}
s_currentContext.remove();
if (s_logger.isTraceEnabled()) {


log.info("unregistered");
}
}

};