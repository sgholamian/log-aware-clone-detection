//,temp,sample_4949.java,2,18,temp,sample_1684.java,2,15
//,3
public class xxx {
public static void stopServices(Collection<?> services) throws Exception {
if (services == null) {
return;
}
Exception firstException = null;
for (Object value : services) {
try {
stopService(value);
} catch (Exception e) {
if (LOG.isDebugEnabled()) {


log.info("caught exception stopping service");
}
}
}
}

};