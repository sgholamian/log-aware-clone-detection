//,temp,sample_4361.java,2,17,temp,sample_4349.java,2,17
//,3
public class xxx {
public void dummy_method(){
ugi.doAs ( new PrivilegedExceptionAction<Void>() {
public Void run() throws StreamingException {
recordWriter.closeBatch();
return null;
}
}
);
try {
FileSystem.closeAllForUGI(ugi);
} catch (IOException exception) {


log.info("could not clean up file system handles for ugi");
}
}

};