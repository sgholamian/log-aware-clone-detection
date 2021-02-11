//,temp,sample_4524.java,2,13,temp,sample_2373.java,2,14
//,3
public class xxx {
private void close(Closeable... closeables) {
try {
if (closeables != null) {
for (Closeable closeable : closeables) {
closeable.close();
}
}
} catch (Exception e) {


log.info("exception occurred while closing the object");
}
}

};