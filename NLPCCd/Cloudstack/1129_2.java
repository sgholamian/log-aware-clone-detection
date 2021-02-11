//,temp,sample_4457.java,2,12,temp,sample_4458.java,2,13
//,3
public class xxx {
public static void ensureRoute(String address) {
if (ensureRouteMethod != null) {
try {
ensureRouteMethod.invoke(ConsoleProxy.context, address);
} catch (IllegalAccessException e) {
} catch (InvocationTargetException e) {


log.info("unable to invoke ensureroute due to");
}
}
}

};