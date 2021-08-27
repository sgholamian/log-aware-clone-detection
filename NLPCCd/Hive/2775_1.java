//,temp,sample_5023.java,2,17,temp,sample_5021.java,2,16
//,3
public class xxx {
public void dummy_method(){
try {
ret = nextKeyBuffer();
this.currentValueBuffer();
} catch (IOException ioe) {
String msg = ioe.getMessage();
if (msg != null && msg.startsWith(BLOCK_MISSING_MESSAGE)) {
throw ioe;
}
ret = -1;
} catch (Throwable t) {


log.info("ignoring unknown error in after offset");
}
}

};