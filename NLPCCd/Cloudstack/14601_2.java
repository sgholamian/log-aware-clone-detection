//,temp,sample_6883.java,2,19,temp,sample_6919.java,2,19
//,2
public class xxx {
public void dummy_method(){
if (host.getType() == Type.ConsoleProxy) {
String name = host.getName();
if (s_logger.isInfoEnabled()) {
}
if (name != null && name.startsWith("v-")) {
String[] tokens = name.split("-");
long proxyVmId = 0;
try {
proxyVmId = Long.parseLong(tokens[1]);
} catch (NumberFormatException e) {


log.info("unexpected exception");
}
}
}
}

};