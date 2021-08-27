//,temp,sample_3493.java,2,12,temp,sample_5556.java,2,14
//,3
public class xxx {
private void close(Closeable is) {
if(is == null) {
return;
}
try {
is.close();
}
catch (IOException ex) {


log.info("failed to close inputstream");
}
}

};