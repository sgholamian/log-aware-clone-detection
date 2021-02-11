//,temp,sample_3048.java,2,15,temp,sample_3047.java,2,14
//,2
public class xxx {
public static Long getPid() {
String name = ManagementFactory.getRuntimeMXBean().getName();
String[] nameParts = name.split("@");
if (nameParts.length == 2) {
try {
return Long.parseLong(nameParts[0]);
} catch (NumberFormatException ex) {


log.info("failed to get pid from");
}
}
}

};